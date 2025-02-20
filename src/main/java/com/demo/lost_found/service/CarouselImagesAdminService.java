package com.demo.lost_found.service;

import com.demo.lost_found.pojo.CarouselImages;
import com.demo.lost_found.rep.BaseResponse;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface CarouselImagesAdminService {
    BaseResponse<List<CarouselImages>> getList(String type);

    void increaseClicks(Integer id);

    BaseResponse enable(Integer id, String enable);

    void delete(Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    void update(CarouselImages carouselImages);

    void add(CarouselImages carouselImages);
}
