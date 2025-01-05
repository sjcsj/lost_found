package com.demo.lost_found.controller;

import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.PhoneCodeForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/loginByUsername")
    @ApiOperation("用户名密码登录")
    public BaseResponse<String> loginByUsername(@RequestBody User user) {
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
    public BaseResponse<String> loginByPhone(@RequestBody PhoneCodeForm phoneCodeForm) {
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
        userService.sendPhoneCode(phone);
        return new BaseResponse(200, "验证码发送成功", null);
    }
}
