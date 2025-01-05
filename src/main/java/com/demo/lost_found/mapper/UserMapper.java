package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User selectByUsername(@Param("username") String username, @Param("role") String role);

    void addUser(User user);

    User selectByPhone(@Param("phone") String phone);

    User selectByUsernameAllRole(String username);

}
