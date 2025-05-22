package com.demo.lost_found.service;

import com.demo.lost_found.pojo.LeaveMessage;
import com.demo.lost_found.rep.BaseResponse;

import java.util.List;
import java.util.Map;

public interface LeaveMessageService {
    BaseResponse<List<LeaveMessage>> getMessage();

    BaseResponse submit(Map<String, String> map);

    BaseResponse<List<LeaveMessage>> getMessageByUsername(String username);

    BaseResponse delete(Integer id);

    BaseResponse<List<LeaveMessage>> getMessageOfCurrentUser();
}
