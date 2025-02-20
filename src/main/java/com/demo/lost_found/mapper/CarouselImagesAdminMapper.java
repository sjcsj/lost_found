package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.CarouselImages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarouselImagesAdminMapper {
    List<CarouselImages> getListByClicks();

    List<CarouselImages> getListBySort();

    void increaseClicks(@Param("id") Integer id);

    void enable(@Param("id") Integer id, @Param("enable") String enable);

    void delete(@Param("id") Integer id);

    void update(CarouselImages carouselImages);

    void add(CarouselImages carouselImages);

    CarouselImages getById(@Param("id") Integer id);
}
