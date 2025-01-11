package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.Authority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityAdminMapper {
    List<Authority> getAll();
}
