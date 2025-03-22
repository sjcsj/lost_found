package com.demo.lost_found.service.impl;

import com.demo.lost_found.mapper.CommentLostMapper;
import com.demo.lost_found.pojo.CommentLost;
import com.demo.lost_found.service.CommentLostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CommentLostServiceImpl implements CommentLostService {

    @Autowired
    private CommentLostMapper commentLostMapper;

    @Override
    public List<CommentLost> getCommentById(Integer id) {
        return commentLostMapper.getCommentById(id);
    }
}
