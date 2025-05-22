package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.Announcement;
import com.demo.lost_found.pojo.form.AnnouncementForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnnouncementAdminMapper {

    List<Announcement> getList(AnnouncementForm announcementForm);

    List<Integer> getUserList();

    void add(Announcement announcement);

    void update(Announcement announcement);

    Announcement getById(@Param("id") Integer id);

    void delete(@Param("id") Integer id);
}
