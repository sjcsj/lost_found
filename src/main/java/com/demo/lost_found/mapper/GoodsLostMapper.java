package com.demo.lost_found.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsLostMapper {
    String getById(@Param("id") Integer id);

    void add(@Param("id") Integer id, @Param("imageUrl") String imageUrl);
}
