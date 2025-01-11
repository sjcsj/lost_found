package com.demo.lost_found.service;

import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.UserAdminForm;
import com.demo.lost_found.rep.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserAdminService {

    List<User> getUserList(UserAdminForm userAdminForm);

    BaseResponse<User> getUserInfoById(Integer id);

    BaseResponse<String> addUser(User user);

    void deleteByIds(List<Integer> ids);

    void export(List<User> users, HttpServletResponse httpServletResponse) throws IOException;

    BaseResponse<String> editUser(User user);

    BaseResponse<String> reSetPassword(User user);
}
