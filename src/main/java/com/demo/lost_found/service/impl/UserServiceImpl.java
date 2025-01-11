package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.demo.lost_found.contants.RedisConstants;
import com.demo.lost_found.mapper.UserMapper;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.PhoneCodeForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.UserService;
import com.demo.lost_found.utils.Auth0JwtUtils;
import com.demo.lost_found.utils.VerificationCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.aliyun.teautil.Common.assertAsString;
import static com.demo.lost_found.utils.MD5Util.md5Lower;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("shortMessageClient")
    private Client shortMessageClient;

    @Value("${aliyun.short-message.signname}")
    private String signName;

    @Value("${aliyun.short-message.templatecode}")
    private String templateCode;

    @Value("${minio.server-url}")
    private String minioUrl;

    @Override
    public BaseResponse<String> login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String role = user.getRole();
        // 查询数据
        User user1 = userMapper.selectByUsername(username, role);
        if (user1 == null) {
            return new BaseResponse(400, "用户名不存在", null);
        }
        if (user1.getDeletedAt() != null) {
            return new BaseResponse(400, "该用户已被删除", null);
        }
        if (!md5Lower(password).equals(user1.getPassword())) {
            return new BaseResponse(400, "密码错误", null);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        String token = Auth0JwtUtils.sign(map);
        // 将token存放到redis，value设置为用户id
        stringRedisTemplate.opsForValue().set(RedisConstants.TOKEN_PREFIX + token, user1.getId() + "", 30, TimeUnit.MINUTES);
        return new BaseResponse(200, "登录成功", token);
    }

    @Override
    public BaseResponse<String> loginByPhone(PhoneCodeForm phoneCodeForm) {
        String phone = phoneCodeForm.getPhone();
        String code = phoneCodeForm.getCode();
        // 查询redis中是否有该手机号
        String redisCode = stringRedisTemplate.opsForValue().get(RedisConstants.USER_PHONE_CODE_LOGIN + phone);
        if (redisCode == null) {
            return new BaseResponse<>(400, "验证码输入错误或已失效", null);
        }
        if (!code.equals(redisCode)) {
            return new BaseResponse<>(400, "验证码输入错误", null);
        }
        // 查询用户信息，生成token并返回
        User user = userMapper.selectByPhone(phone);
        if (user.getDeletedAt() != null) {
            return new BaseResponse<>(400, "该用户已被删除", null);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        String token = Auth0JwtUtils.sign(map);
        // 将token存放到redis，value设置为用户id
        stringRedisTemplate.opsForValue().set(RedisConstants.TOKEN_PREFIX + token, user.getId() + "", 30, TimeUnit.MINUTES);
        return new BaseResponse<>(200, "登录成功", token);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    @Override
    public BaseResponse register(User user) {
        // 判断用户名是否唯一，不能和普通用户管理员重名，也不能跟已删除的用户重名
        User user1 = userMapper.selectByUsernameAllRole(user.getUsername());
        if (user1 != null) {
            // 跟其他用户名冲突
            return new BaseResponse(400, "用户名已被他人使用，请更换其他用户名", null);
        }
        // 唯一，加密密码，添加基础信息后入库
        user.setPassword(md5Lower(user.getPassword()));
        user.setRole("user");
        user.setGender("男");
        user.setAvatar(minioUrl + "/lost-found/default.jpg");
        user.setCreatedAt(DateUtil.now());
        userMapper.addUser(user);
        return new BaseResponse(200, "注册成功，请登录", null);
    }

    @Override
    public void sendPhoneCode(String phone) {
        // 生成验证码
        String code = VerificationCodeUtils.generateNumericCode();
        log.info("生成的验证码为:{}", code);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            shortMessageClient.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            log.error("短信发送出现异常:{}", error.getMessage());
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            log.error("短信发送出现异常:{}", error.getMessage());
        }
        // 将手机号和验证码存到redis中，方便后续验证，TTL为1分钟
        log.info("将验证码存入redis");
        stringRedisTemplate.opsForValue().set(RedisConstants.USER_PHONE_CODE_LOGIN + phone, code, 1, TimeUnit.MINUTES);
    }

}














