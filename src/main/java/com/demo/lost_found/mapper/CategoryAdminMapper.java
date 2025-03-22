package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryAdminMapper {
    List<Category> getListBySort();

    List<Category> getListByClicks();

    void add(Category category);

    void update(Category category);

    void delete(@Param("id") Integer id);

    void incr(@Param("id") Integer categoryId);

    Category getById(@Param("id") Integer id);
}
