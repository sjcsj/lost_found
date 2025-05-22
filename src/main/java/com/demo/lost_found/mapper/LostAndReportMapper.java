package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.LostAndReport;
import com.demo.lost_found.pojo.form.ReviewForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LostAndReportMapper {

    List<LostAndReport> getList();

    void add(LostAndReport lostAndReport);

    LostAndReport getById(@Param("id") Integer id);

    void mark(@Param("id") Integer id);

    List<LostAndReport> getAll(LostAndReport lostAndReport);

    Integer getCategoryIdById(Integer id);

    void update(LostAndReport lostAndReport);

    void deleteById(Integer id);

    void review(ReviewForm reviewForm);

    List<LostAndReport> getAllByUserId(Integer id);

    Integer getCount();

    Integer getCount1();

    Integer getCount2();

    Integer getCount3();

    Integer getCount4();
}
