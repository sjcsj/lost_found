package com.demo.lost_found.service;

import com.demo.lost_found.pojo.CommentFound;

import java.util.List;

public interface CommentFoundService {
    List<CommentFound> getCommentById(Integer id);
}
