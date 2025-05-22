package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.LeaveMessage;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.LeaveMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/leaveMessage")
public class LeaveMessageAdminController {

    @Autowired
    private LeaveMessageService leaveMessageService;

    /**
     * 根据用户名模糊搜索留言，倒序排序
     */
    @GetMapping("/getMessage")
    public BaseResponse<List<LeaveMessage>> getMessage(@RequestParam("username") String username) {
        return leaveMessageService.getMessageByUsername(username);
    }

    /**
     * 根据id删除对应的留言
     */
    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam("id") Integer id) {
        return leaveMessageService.delete(id);
    }
}




























