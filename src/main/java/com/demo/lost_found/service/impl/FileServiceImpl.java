package com.demo.lost_found.service.impl;

import com.demo.lost_found.contants.MinioConstants;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.server-url}")
    private String serverUrl;

    @Autowired
    private MinioClient minioClient;

    @Override
    public BaseResponse<String> upload(MultipartFile file, String type) throws IOException {
        // 生成唯一的对象名称（避免文件名冲突）
        String originalFilename = file.getOriginalFilename();
        String objectName = "";
        if ("avatar".equals(type)) {
            objectName = MinioConstants.USER_AVATAR + "/" + System.currentTimeMillis() + "_" + originalFilename;
        } else if ("carouselImage".equals(type)) {
            objectName = MinioConstants.USER_CAROUSEL_IMAGE + "/" + System.currentTimeMillis() + "_" + originalFilename;
        } else if ("promotionalVideos".equals(type)) {
            objectName = MinioConstants.PROMOTIONAL_VIDEOS + "/" + System.currentTimeMillis() + "_" + originalFilename;
        } else if ("lostAndFound".equals(type)) {
            objectName = MinioConstants.LOST_AND_FOUND + "/" + System.currentTimeMillis() + "_" + originalFilename;
        } else if ("lostAndReport".equals(type)) {
            objectName = MinioConstants.LOST_AND_REPORT + "/" + System.currentTimeMillis() + "_" + originalFilename;
        } else if ("announcementImage".equals(type)) {
            objectName = MinioConstants.ANNOUNCEMENT_IMAGE + "/" + System.currentTimeMillis() + "_" + originalFilename;
        } else if ("recognizeImage".equals(type)) {
            objectName = MinioConstants.RECOGNIZE_IMAGE + "/" + System.currentTimeMillis() + "_" + originalFilename;
        }

        try (InputStream inputStream = file.getInputStream()) {
            // 获取文件的输入流并上传到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName) // 存储桶名称
                            .object(objectName) // 对象名称
                            .stream(inputStream, file.getSize(), -1) // 输入流、文件大小、分片大小（-1 表示不分片）
                            .contentType(file.getContentType()) // 设置文件类型
                            .build());

            // 返回上传后的文件路径
            String fileUrl = serverUrl + "/" + bucketName + "/" + objectName;
            log.info("图片上传成功: {}", fileUrl);
            return new BaseResponse<>(200, "上传成功", fileUrl);

        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("上传失败: {}", e.getMessage());
            return new BaseResponse<>(400, "上传失败", null);
        }

    }

    @Override
    public void delete(String url) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String objectName = extractObjectName(url);
        if (!StringUtils.isEmpty(objectName)) {
            // 删除对象
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            log.info("图片{}已删除", url);
        }
    }

    /**
     * 从完整的图片地址中提取图片名称，适用于所有类型的图片
     */
    private String extractObjectName(String imageUrl) {
        String baseUrl = serverUrl + "/" + bucketName + "/";
        if (imageUrl.startsWith(baseUrl)) {
            return imageUrl.substring(baseUrl.length());
        }
        return null;
    }
}
