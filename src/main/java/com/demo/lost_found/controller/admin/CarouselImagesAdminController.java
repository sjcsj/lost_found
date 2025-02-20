package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.CarouselImages;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.CarouselImagesAdminService;
import com.demo.lost_found.service.FileService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/admin/carouselImages")
public class CarouselImagesAdminController {

    @Autowired
    private CarouselImagesAdminService carouselImagesAdminService;

    /**
     * 获取轮播图列表，type为1是按排序字段排序，type为2是按点击次数排序
     * @return
     */
    @GetMapping("/getList")
    public BaseResponse<List<CarouselImages>> getList(@RequestParam("type") String type) {
        return carouselImagesAdminService.getList(type);
    }

    /**
     * 增加该轮播图的点击次数
     * @param id
     */
    @GetMapping("/increaseClicks")
    public void increaseClicks(@RequestParam("id") Integer id) {
        carouselImagesAdminService.increaseClicks(id);
    }

    /**
     * 启用禁用轮播图
     */
    @GetMapping("/enable")
    public BaseResponse enable(@RequestParam("id") Integer id, @RequestParam("enable") Boolean enable) {
        String enable1;
        if (enable) {
            enable1 = "true";
        } else {
            enable1 = "false";
        }
        return carouselImagesAdminService.enable(id, enable1);
    }

    /**
     * 删除轮播图
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam("id") Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        carouselImagesAdminService.delete(id);
        return new BaseResponse(200, "已删除", null);
    }

    /**
     * 更新轮播图
     * @param carouselImages
     * @return
     */
    @PostMapping("/update")
    public BaseResponse update(@RequestBody CarouselImages carouselImages) {
        carouselImagesAdminService.update(carouselImages);
        return new BaseResponse(200, "更新成功", null);
    }

    /**
     * 新增轮播图
     * @param carouselImages
     * @return
     */
    @PostMapping("/add")
    public BaseResponse add(@RequestBody CarouselImages carouselImages) {
        carouselImagesAdminService.add(carouselImages);
        return new BaseResponse(200, "新增成功", null);
    }
}























