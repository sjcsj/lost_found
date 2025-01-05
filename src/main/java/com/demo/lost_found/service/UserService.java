package com.demo.lost_found.service;

import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.PhoneCodeForm;
import com.demo.lost_found.rep.BaseResponse;

public interface UserService {

    BaseResponse<String> login(User user);

    BaseResponse register(User user);

    void sendPhoneCode(String phone);

    BaseResponse<String> loginByPhone(PhoneCodeForm phoneCodeForm);

    User getUserByPhone(String phone);
}
