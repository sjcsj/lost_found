package com.demo.lost_found.controller;

import com.demo.lost_found.contants.RedisConstants;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.pojo.form.PhoneCodeForm;
import com.demo.lost_found.pojo.vo.ContactWayVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/loginByUsername")
    @ApiOperation("用户名密码登录")
    public BaseResponse loginByUsername(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public BaseResponse register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 只有普通用户可以使用手机号验证码登录，因此该接口的查询默认role为user
     * @return
     */
    @PostMapping("/loginByPhone")
    @ApiOperation("手机号验证码登录")
    public BaseResponse loginByPhone(@RequestBody PhoneCodeForm phoneCodeForm) {
        return userService.loginByPhone(phoneCodeForm);
    }

    @GetMapping("/sendPhoneCode")
    @ApiOperation("发送验证码到指定的手机号,用于登录")
    public BaseResponse sendPhoneCode(@RequestParam("phone") String phone) {
        // 先查看该手机号是否有对应用户，如果没有则提醒用户先注册
        User user = userService.getUserByPhone(phone);
        if (user == null) {
            return new BaseResponse(400, "当前用户未注册，请注册账号后绑定手机号才可使用手机号登录", null);
        }
        userService.sendPhoneCode(phone, RedisConstants.USER_PHONE_CODE_LOGIN);
        return new BaseResponse(200, "验证码发送成功", null);
    }

    @GetMapping("/sendPhoneCodeToUnbind")
    @ApiOperation("发送验证码到指定的手机号,用于解绑手机号")
    public BaseResponse sendPhoneCodeToUnbind(@RequestParam("phone") String phone) {
        userService.sendPhoneCode(phone, RedisConstants.USER_PHONE_CODE_UNBIND);
        return new BaseResponse(200, "验证码发送成功", null);
    }

    @PostMapping("/unBindPhone")
    @ApiOperation("解绑手机号")
    public BaseResponse unBindPhone(@RequestBody PhoneCodeForm phoneCodeForm) {
        return userService.unBindPhone(phoneCodeForm);
    }

    @GetMapping("/sendPhoneCodeToBind")
    @ApiOperation("发送验证码到指定的手机号,用于绑定手机号")
    public BaseResponse sendPhoneCodeToBind(@RequestParam("phone") String phone) {
        // 先查看该手机号是否有对应用户，如果有则提醒用户无法绑定
        User user = userService.getUserByPhone(phone);
        if (user != null) {
            return new BaseResponse(400, "当前手机号已绑定其他用户，无法同时绑定多个用户", null);
        }
        userService.sendPhoneCode(phone, RedisConstants.USER_PHONE_CODE_BIND);
        return new BaseResponse(200, "验证码发送成功", null);
    }

    @PostMapping("/bindPhone")
    @ApiOperation("绑定手机号")
    public BaseResponse bindPhone(@RequestBody PhoneCodeForm phoneCodeForm) {
        return userService.bindPhone(phoneCodeForm);
    }

    /**
     * 有手机号的用户才能访问
     * @return
     */
    @GetMapping("/getContactWay")
    @ApiOperation("获取当前用户拥有的联系方式")
    public BaseResponse<List<ContactWayVO>> getContactWay() {
        return userService.getContactWay();
    }

    /**
     * 获取当前用户id
     */
    @GetMapping("/getUserId")
    public BaseResponse<Integer> getUserId() {
        return new BaseResponse<>(200, "获取成功", UserContext.getCurrentUser().getId());
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/getUser")
    public BaseResponse<User> getUser() {
        return new BaseResponse<>(200, "获取成功", UserContext.getCurrentUser());
    }

    /**
     * 根据id修改用户头像
     * @return
     */
    @PostMapping("/updateAvatar")
    public BaseResponse updateAvatar(@RequestBody User user) {
        return userService.updateAvatar(user);
    }

    /**
     * 根据id修改微信号和性别
     * @return
     */
    @PostMapping("/update")
    public BaseResponse update(@RequestBody User user) {
        return userService.update(user);
    }
}
