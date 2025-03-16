package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.PromotionalVideosMapper;
import com.demo.lost_found.pojo.PromotionalVideos;
import com.demo.lost_found.pojo.form.PromotionalVideosForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.FileService;
import com.demo.lost_found.service.PromotionalVideosService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PromotionalVideosServiceImpl implements PromotionalVideosService {

    @Autowired
    private FileService fileService;

    @Autowired
    private PromotionalVideosMapper promotionalVideosMapper;

    @Override
    public BaseResponse<List<PromotionalVideos>> getList(PromotionalVideosForm promotionalVideosForm) {
        List<PromotionalVideos> list = promotionalVideosMapper.getList(promotionalVideosForm);
        return new BaseResponse<>(200, "搜索成功", list);
    }

    @Override
    public BaseResponse addPromotionalVideos(PromotionalVideos promotionalVideos) {
        promotionalVideos.setCreatedAt(DateUtil.now());
        promotionalVideosMapper.addPromotionalVideos(promotionalVideos);
        return new BaseResponse(200, "新增成功", null);
    }

    @Override
    public BaseResponse delete(PromotionalVideos promotionalVideos) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 先删除文件
        fileService.delete(promotionalVideos.getVideoUrl());
        // 再清除记录
        promotionalVideosMapper.delete(promotionalVideos);
        return new BaseResponse(200, "删除成功", null);
    }

    @Override
    public BaseResponse update(PromotionalVideos promotionalVideos) {
        promotionalVideosMapper.update(promotionalVideos);
        return new BaseResponse(200, "修改成功", null);
    }
}
