package com.demo.lost_found.service;

import com.demo.lost_found.rep.BaseResponse;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FileService {
    BaseResponse<String> upload(MultipartFile file, String type) throws IOException;

    void delete(String url) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
