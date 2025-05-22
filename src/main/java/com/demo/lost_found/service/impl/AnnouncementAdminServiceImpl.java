package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.AnnouncementAdminMapper;
import com.demo.lost_found.mapper.UserAdminMapper;
import com.demo.lost_found.pojo.Announcement;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.pojo.form.AnnouncementForm;
import com.demo.lost_found.pojo.vo.AnnouncementUserVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.AnnouncementAdminService;
import com.demo.lost_found.service.FileService;
import com.demo.lost_found.service.UserAdminService;
import io.minio.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementAdminServiceImpl implements AnnouncementAdminService {

    @Autowired
    private AnnouncementAdminMapper announcementAdminMapper;

    @Autowired
    private UserAdminMapper userAdminMapper;

    @Autowired
    private FileService fileService;

    @Override
    public BaseResponse<List<Announcement>> getList(AnnouncementForm announcementForm) {
        List<Announcement> list = announcementAdminMapper.getList(announcementForm);
        list.forEach(e -> {
            User user = userAdminMapper.getUserInfoById(e.getCreatedBy());
            e.setUsername(user.getUsername());
        });
        return new BaseResponse<>(200, "搜索成功", list);
    }

    @Override
    public BaseResponse<List<AnnouncementUserVO>> getUserList() {
        // 先查询现有公告创建人有哪些
        List<Integer> list = announcementAdminMapper.getUserList();
        List<AnnouncementUserVO> res = new ArrayList<>();
        list.forEach(e -> {
            AnnouncementUserVO item = new AnnouncementUserVO();
            item.setUserId(e);
            User user = userAdminMapper.getUserInfoById(e);
            item.setUsername(user.getUsername());
            res.add(item);
        });
        return new BaseResponse<>(200, "获取成功", res);
    }

    @Override
    public BaseResponse<String> add(Announcement announcement) {
        Integer id = UserContext.getCurrentUser().getId();
        announcement.setCreatedBy(id);
        announcement.setUpdatedAt(DateUtil.now());
        announcementAdminMapper.add(announcement);
        return new BaseResponse<>(200, "新增成功", null);
    }

    @Override
    public BaseResponse<String> update(Announcement announcement) {
        Integer id = UserContext.getCurrentUser().getId();
        announcement.setCreatedBy(id);
        announcement.setUpdatedAt(DateUtil.now());
        announcementAdminMapper.update(announcement);
        return new BaseResponse<>(200, "修改成功", null);
    }

    @Override
    public BaseResponse<String> delete(Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 根据id查询
        Announcement announcement = announcementAdminMapper.getById(id);
        // 删除图片
        if (StringUtils.isNotEmpty(announcement.getImageUrl())) {
            fileService.delete(announcement.getImageUrl());
        }
        announcementAdminMapper.delete(id);
        return new BaseResponse<>(200, "删除成功", null);
    }

    @Override
    public BaseResponse<Announcement> getById(Integer id) {
        Announcement announcement = announcementAdminMapper.getById(id);
        User user = userAdminMapper.getUserInfoById(announcement.getCreatedBy());
        announcement.setUsername(user.getUsername());
        return new BaseResponse<>(200, "获取成功", announcement);
    }
}










