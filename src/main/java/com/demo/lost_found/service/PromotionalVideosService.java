package com.demo.lost_found.service;

import com.demo.lost_found.pojo.PromotionalVideos;
import com.demo.lost_found.pojo.form.PromotionalVideosForm;
import com.demo.lost_found.rep.BaseResponse;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface PromotionalVideosService {
    BaseResponse<List<PromotionalVideos>> getList(PromotionalVideosForm promotionalVideosForm);

    BaseResponse addPromotionalVideos(PromotionalVideos promotionalVideos);

    BaseResponse delete(PromotionalVideos promotionalVideos) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    BaseResponse update(PromotionalVideos promotionalVideos);
}
