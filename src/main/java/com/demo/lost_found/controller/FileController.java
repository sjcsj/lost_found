package com.demo.lost_found.controller;

import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.FileService;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 头像上传，返回url，专门用于上传头像
     */
    @PostMapping("/upload")
    public BaseResponse<String> upload(@RequestPart("file") MultipartFile file) throws IOException {
        return fileService.upload(file, "avatar");
    }

    /**
     * 轮播图上传，返回url，专门用于上传轮播图
     */
    @PostMapping("/uploadCarouselImage")
    public BaseResponse<String> uploadCarouselImage(@RequestPart("file") MultipartFile file) throws IOException {
        return fileService.upload(file, "carouselImage");
    }

    /**
     * 视频上传，返回url，专门用于上传宣传视频
     */
    @PostMapping("/uploadPromotionalVideos")
    public BaseResponse<String> uploadPromotionalVideos(@RequestPart("file") MultipartFile file) throws IOException {
        return fileService.upload(file, "promotionalVideos");
    }

    /**
     * 删除文件，适用于所有图片类型
     */
    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam("url") String url) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("将要删除{}", url);
        fileService.delete(url);
        return new BaseResponse(200, "删除成功", null);
    }

}
