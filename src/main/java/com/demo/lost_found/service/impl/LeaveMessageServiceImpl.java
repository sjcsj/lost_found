package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.LeaveMessageMapper;
import com.demo.lost_found.mapper.UserAdminMapper;
import com.demo.lost_found.pojo.LeaveMessage;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.pojo.form.BlackInfoForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.BlackListService;
import com.demo.lost_found.service.LeaveMessageService;
import com.demo.lost_found.utils.BaiduUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    @Autowired
    private LeaveMessageMapper leaveMessageMapper;

    @Autowired
    private UserAdminMapper userAdminMapper;

    @Autowired
    private BaiduUtil baiduUtil;

    @Autowired
    private BlackListService blackListService;

    @Override
    public BaseResponse<List<LeaveMessage>> getMessage() {
        List<LeaveMessage> list = leaveMessageMapper.getMessage();
        return new BaseResponse<>(200, "获取成功", list);
    }

    @Override
    public BaseResponse submit(Map<String, String> map) {
        String s = map.get("content");
        User currentUser = UserContext.getCurrentUser();
        // 验证评论是否合规
        boolean isOK = baiduUtil.textDetection(s);
        if (!isOK) {
            // 不合规，封禁该用户30分钟
            BlackInfoForm blackInfoForm = new BlackInfoForm();
            blackInfoForm.setUserId(currentUser.getId());
            Date date = DateUtil.offsetMinute(new Date(), 30);
            String formattedTime = DateUtil.formatDateTime(date);
            blackInfoForm.setDeadline(formattedTime);
            blackInfoForm.setReason("发布不当言论");
            blackInfoForm.setNotes("发送留言前请仔细确认是否恰当");
            blackListService.addBlackInfo(blackInfoForm);
            return new BaseResponse<>(400, "发布失败", null);
        }
        LeaveMessage leaveMessage = new LeaveMessage();
        leaveMessage.setUserId(currentUser.getId());
        leaveMessage.setComment(s);
        leaveMessageMapper.insert(leaveMessage);
        return new BaseResponse(200, "新增成功", null);
    }

    @Override
    public BaseResponse<List<LeaveMessage>> getMessageByUsername(String username) {
        List<LeaveMessage> list = leaveMessageMapper.getMessage();
        if (StringUtils.isEmpty(username)) {
            for (LeaveMessage item : list) {
                User user = userAdminMapper.getUserInfoById(item.getUserId());
                if (user != null) {
                    item.setUsername(user.getUsername());
                    item.setPhone(user.getPhone());
                }
            }
            return new BaseResponse<>(200, "获取成功", list);
        }
        List<LeaveMessage> filteredList = new ArrayList<>();
        for (LeaveMessage item : list) {
            User user = userAdminMapper.getUserInfoById(item.getUserId());
            if (user != null && user.getUsername().contains(username)) {
                item.setUsername(user.getUsername());
                item.setPhone(user.getPhone());
                filteredList.add(item);
            }
        }
        return new BaseResponse<>(200, "获取成功", filteredList);
    }

    @Override
    public BaseResponse delete(Integer id) {
        leaveMessageMapper.delete(id);
        return new BaseResponse(200, "删除成功", null);
    }

    @Override
    public BaseResponse<List<LeaveMessage>> getMessageOfCurrentUser() {
        User user = UserContext.getCurrentUser();
        List<LeaveMessage> list = leaveMessageMapper.getMessageOfCurrentUser(user.getId());
        return new BaseResponse<>(200, "获取成功", list);
    }
}
