package com.demo.lost_found.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsFoundMapper {
    void add(@Param("id") Integer id, @Param("imageUrl") String imageUrl);

    String getById(@Param("id") Integer id);
}
