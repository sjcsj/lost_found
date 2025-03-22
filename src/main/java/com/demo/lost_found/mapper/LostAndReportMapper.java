package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.LostAndReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LostAndReportMapper {

    List<LostAndReport> getList();

    void add(LostAndReport lostAndReport);

    LostAndReport getById(@Param("id") Integer id);

    void mark(@Param("id") Integer id);
}
