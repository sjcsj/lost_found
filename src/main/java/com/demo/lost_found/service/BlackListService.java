package com.demo.lost_found.service;

import com.demo.lost_found.pojo.BlackList;
import com.demo.lost_found.pojo.form.BlackInfoForm;
import com.demo.lost_found.pojo.form.BlackListAdminForm;
import com.demo.lost_found.rep.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BlackListService {
    List<BlackList> getBlackList(BlackListAdminForm blackListAdminForm);

    BaseResponse<BlackList> getUserStatus(Integer userId);

    BaseResponse<String> addBlackInfo(BlackInfoForm blackInfoForm);

    void export(List<BlackList> blackList, HttpServletResponse httpServletResponse) throws IOException;

    BaseResponse<String> releaseUserById(Integer id);

    BaseResponse<String> updateBlackInfo(BlackList blackList);

    BaseResponse<List<BlackList>> getBlackHistory(Integer id);
}
