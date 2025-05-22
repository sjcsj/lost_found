package com.demo.lost_found.controller;

import com.demo.lost_found.pojo.LeaveMessage;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.LeaveMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaveMessage")
public class LeaveMessageController {

    @Autowired
    private LeaveMessageService leaveMessageService;

    /**
     * 获取留言，倒序排序
     */
    @GetMapping("/getMessage")
    public BaseResponse<List<LeaveMessage>> getMessage() {
        return leaveMessageService.getMessage();
    }

    /**
     * 提交留言
     */
    @PostMapping("/submit")
    public BaseResponse submit(@RequestBody Map<String, String> map) {
        return leaveMessageService.submit(map);
    }

    /**
     * 获取当前用户的所有留言，并倒序排序
     */
    @GetMapping("/getMessageOfCurrentUser")
    public BaseResponse<List<LeaveMessage>> getMessageOfCurrentUser() {
        return leaveMessageService.getMessageOfCurrentUser();
    }

}












