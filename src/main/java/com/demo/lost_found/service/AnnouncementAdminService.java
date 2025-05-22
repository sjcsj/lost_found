package com.demo.lost_found.service;

import com.demo.lost_found.pojo.Announcement;
import com.demo.lost_found.pojo.form.AnnouncementForm;
import com.demo.lost_found.pojo.vo.AnnouncementUserVO;
import com.demo.lost_found.rep.BaseResponse;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface AnnouncementAdminService {
    BaseResponse<List<Announcement>> getList(AnnouncementForm announcementForm);

    BaseResponse<List<AnnouncementUserVO>> getUserList();

    BaseResponse<String> add(Announcement announcement);

    BaseResponse<String> update(Announcement announcement);

    BaseResponse<String> delete(Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    BaseResponse<Announcement> getById(Integer id);
}
