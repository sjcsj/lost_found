package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.form.ReviewForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LostAndFoundMapper {

    void add(LostAndFound lostAndFound);

    List<LostAndFound> getList();

    LostAndFound getById(@Param("id") Integer id);

    void mark(@Param("id") Integer id);

    List<LostAndFound> getAll(LostAndFound lostAndFound);

    void update(LostAndFound lostAndFound);

    Integer getCategoryIdById(@Param("id") Integer id);

    void deleteById(Integer id);

    void review(ReviewForm reviewForm);

    List<LostAndFound> getAllByUserId(Integer id);

    Integer getCount();

    Integer getCount1();

    Integer getCount2();

    Integer getCount3();

    Integer getCount4();
}
