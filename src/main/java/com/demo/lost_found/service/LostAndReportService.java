package com.demo.lost_found.service;

import com.demo.lost_found.pojo.CommentLost;
import com.demo.lost_found.pojo.LostAndReport;
import com.demo.lost_found.pojo.form.LostAndReportAddForm;
import com.demo.lost_found.rep.BaseResponse;

import java.util.List;

public interface LostAndReportService {
    BaseResponse<List<LostAndReport>> getList();

    BaseResponse<String> add(LostAndReportAddForm lostAndReportAddForm);

    BaseResponse<LostAndReport> getById(Integer id);

    BaseResponse<String> mark(Integer id);

    BaseResponse<List<CommentLost>> getCommentById(Integer id);

    BaseResponse<String> submitComment(Integer id, String comment);
}
