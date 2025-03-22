package com.demo.lost_found.service;

import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.PhoneCodeForm;
import com.demo.lost_found.pojo.vo.ContactWayVO;
import com.demo.lost_found.rep.BaseResponse;

import java.util.List;

public interface UserService {

    BaseResponse login(User user);

    BaseResponse register(User user);

    void sendPhoneCode(String phone);

    BaseResponse loginByPhone(PhoneCodeForm phoneCodeForm);

    User getUserByPhone(String phone);

    BaseResponse<List<ContactWayVO>> getContactWay();
}
