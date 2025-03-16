package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.PromotionalVideos;
import com.demo.lost_found.pojo.form.PromotionalVideosForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PromotionalVideosMapper {
    List<PromotionalVideos> getList(PromotionalVideosForm promotionalVideosForm);

    void addPromotionalVideos(PromotionalVideos promotionalVideos);

    void delete(PromotionalVideos promotionalVideos);

    void update(PromotionalVideos promotionalVideos);
}
