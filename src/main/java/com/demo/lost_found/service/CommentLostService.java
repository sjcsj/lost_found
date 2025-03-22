package com.demo.lost_found.service;

import com.demo.lost_found.pojo.CommentLost;

import java.util.List;

public interface CommentLostService {
    List<CommentLost> getCommentById(Integer id);
}
