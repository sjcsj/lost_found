package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.CarouselImagesAdminMapper;
import com.demo.lost_found.pojo.CarouselImages;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.CarouselImagesAdminService;
import com.demo.lost_found.service.FileService;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.jacoco.agent.rt.internal_035b120.core.internal.flow.LabelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CarouselImagesAdminServiceImpl implements CarouselImagesAdminService {

    @Autowired
    private CarouselImagesAdminMapper carouselImagesAdminMapper;

    @Autowired
    private FileService fileService;

    @Override
    public BaseResponse<List<CarouselImages>> getList(String type) {
        List<CarouselImages> list = new ArrayList<>();
        if ("1".equals(type)) {
            // 按排序字段排序
            list = carouselImagesAdminMapper.getListBySort();
        } else if ("2".equals(type)) {
            // 按点击次数排序
            list = carouselImagesAdminMapper.getListByClicks();
        }
        list.forEach(e -> {
            Boolean a = "true".equals(e.getEnable()) ? true : false;
            e.setEnable1(a);
        });
        return new BaseResponse<>(200, "查询成功", list);
    }

    @Override
    public BaseResponse<List<CarouselImages>> getListFront() {
        // 按排序字段排序
        List<CarouselImages> list = carouselImagesAdminMapper.getListBySortAndEnable();
        list.forEach(e -> {
            Boolean a = "true".equals(e.getEnable()) ? true : false;
            e.setEnable1(a);
        });
        return new BaseResponse<>(200, "查询成功", list);
    }

    @Override
    public void increaseClicks(Integer id) {
        carouselImagesAdminMapper.increaseClicks(id);
    }

    @Override
    public BaseResponse enable(Integer id, String enable) {
        carouselImagesAdminMapper.enable(id, enable);
        String s = enable.equals("true") ? "已启用" : "已禁用";
        return new BaseResponse(200, s, null);
    }

    @Override
    public void delete(Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        CarouselImages carouselImages = carouselImagesAdminMapper.getById(id);
        carouselImagesAdminMapper.delete(id);
        //删除minio上的图片
        log.info("将要删除{}", carouselImages.getImageUrl());
        fileService.delete(carouselImages.getImageUrl());
    }

    @Override
    public void update(CarouselImages carouselImages) {
        carouselImagesAdminMapper.update(carouselImages);
    }

    @Override
    public void add(CarouselImages carouselImages) {
        carouselImages.setCreatedAt(DateUtil.now());
        carouselImages.setClicks(0);
        carouselImages.setEnable("true");
        carouselImagesAdminMapper.add(carouselImages);
    }
}


























