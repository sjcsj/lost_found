package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.Authority;
import com.demo.lost_found.pojo.form.AuthorityForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorityAdminMapper {
    List<Authority> getAll(AuthorityForm authorityForm);

    List<Authority> getEnable();

    void enable(@Param("id") Integer id, @Param("enable") String enable);

    void addAuthority(Authority authority);

    void delete(@Param("id") Integer id, @Param("now") String now);

    void update(Authority authority);
}
