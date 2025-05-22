package com.demo.lost_found.service;

import com.demo.lost_found.pojo.CommentFound;
import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.form.LostAndFoundAddForm;
import com.demo.lost_found.pojo.form.ReviewForm;
import com.demo.lost_found.pojo.vo.LostAndFoundVO;
import com.demo.lost_found.rep.BaseResponse;

import java.util.List;

public interface LostAndFoundService {
    BaseResponse<String> add(LostAndFoundAddForm lostAndFoundAddForm);

    BaseResponse<List<LostAndFound>> getList();

    BaseResponse<LostAndFound> getById(Integer id);

    BaseResponse<String> mark(Integer id);

    BaseResponse<List<CommentFound>> getCommentById(Integer id);

    BaseResponse<String> submitComment(Integer id, String comment);

    BaseResponse<List<LostAndFound>> getAll(LostAndFound lostAndFound);

    BaseResponse update(LostAndFound lostAndFound);

    BaseResponse delete(Integer id);

    BaseResponse review(ReviewForm reviewForm);

    BaseResponse<LostAndFoundVO> getAllOfCurrentUser();
}
