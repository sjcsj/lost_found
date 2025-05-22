package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.CommentLost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentLostMapper {
    List<CommentLost> getCommentById(@Param("id") Integer id);

    void add(@Param("id") Integer id, @Param("userId") Integer userId, @Param("comment") String comment);

    void delete(Integer id);
}
