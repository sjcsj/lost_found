package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.Announcement;
import com.demo.lost_found.pojo.form.AnnouncementForm;
import com.demo.lost_found.pojo.vo.AnnouncementUserVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.AnnouncementAdminService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/admin/announcement")
public class AnnouncementAdminController {

    @Autowired
    private AnnouncementAdminService announcementAdminService;

    /**
     * 获取公告列表，可以按标题模糊搜索，可以按管理员id精确搜索，按最后更新时间倒序排序
     */
    @PostMapping("/getList")
    public BaseResponse<List<Announcement>> getList(@RequestBody AnnouncementForm announcementForm) {
        return announcementAdminService.getList(announcementForm);
    }

    /**
     * 获取创建人列表
     */
    @GetMapping("/getUserList")
    public BaseResponse<List<AnnouncementUserVO>> getUserList() {
        return announcementAdminService.getUserList();
    }

    /**
     * 新增公告
     */
    @PostMapping("/add")
    public BaseResponse<String> add(@RequestBody Announcement announcement) {
        return announcementAdminService.add(announcement);
    }

    /**
     * 修改公告
     */
    @PostMapping("/update")
    public BaseResponse<String> update(@RequestBody Announcement announcement) {
        return announcementAdminService.update(announcement);
    }

    /**
     * 删除公告
     */
    @GetMapping("/delete")
    public BaseResponse<String> delete(@RequestParam("id") Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return announcementAdminService.delete(id);
    }

    /**
     * 根据id获取对应的公告
     */
    @GetMapping("/getById")
    public BaseResponse<Announcement> getById(@RequestParam("id") Integer id) {
        return announcementAdminService.getById(id);
    }

}















