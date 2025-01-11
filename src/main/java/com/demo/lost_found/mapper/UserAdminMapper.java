package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.UserAdminForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAdminMapper {

    List<User> getUserListExact(UserAdminForm userAdminForm);

    List<User> getUserListFuzzy(UserAdminForm userAdminForm);

    User getUserInfoById(Integer id);

    List<User> selectByIds(@Param("ids") List<Integer> ids);

    void updateById(User i);
}
