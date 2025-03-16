package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.PromotionalVideos;
import com.demo.lost_found.pojo.form.PromotionalVideosForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.PromotionalVideosService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/admin/promotionalVideos")
public class PromotionalVideosController {

    @Autowired
    private PromotionalVideosService promotionalVideosService;

    /**
     * 根据视频标题、描述模糊搜索
     */
    @PostMapping("/getList")
    public BaseResponse<List<PromotionalVideos>> getList(@RequestBody PromotionalVideosForm promotionalVideosForm) {
        return promotionalVideosService.getList(promotionalVideosForm);
    }

    /**
     * 新增宣传视频
     */
    @PostMapping("/addPromotionalVideos")
    public BaseResponse addPromotionalVideos(@RequestBody PromotionalVideos promotionalVideos) {
        return promotionalVideosService.addPromotionalVideos(promotionalVideos);
    }

    /**
     * 删除宣传视频
     */
    @PostMapping("/delete")
    public BaseResponse delete(@RequestBody PromotionalVideos promotionalVideos) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return promotionalVideosService.delete(promotionalVideos);
    }

    /**
     * 修改宣传视频
     */
    @PostMapping("/update")
    public BaseResponse update(@RequestBody PromotionalVideos promotionalVideos) {
        return promotionalVideosService.update(promotionalVideos);
    }
}























