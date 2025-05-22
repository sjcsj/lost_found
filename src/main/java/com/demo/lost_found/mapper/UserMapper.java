package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.PhoneCodeForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User selectByUsername(@Param("username") String username, @Param("role") String role);

    void addUser(User user);

    User selectByPhone(@Param("phone") String phone);

    User selectByUsernameAllRole(String username);

    void updateAvatar(User user);

    void update(User user);

    void unBindPhone(String phone);

    void bindPhone(PhoneCodeForm phoneCodeForm);
}
