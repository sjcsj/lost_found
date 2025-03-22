package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.LostAndFound;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LostAndFoundMapper {

    void add(LostAndFound lostAndFound);

    List<LostAndFound> getList();

    LostAndFound getById(@Param("id") Integer id);

    void mark(@Param("id") Integer id);
}
