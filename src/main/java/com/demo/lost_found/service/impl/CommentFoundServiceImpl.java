package com.demo.lost_found.service.impl;

import com.demo.lost_found.mapper.CommentFoundMapper;
import com.demo.lost_found.pojo.CommentFound;
import com.demo.lost_found.service.CommentFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CommentFoundServiceImpl implements CommentFoundService {

    @Autowired
    private CommentFoundMapper commentFoundMapper;

    @Override
    public List<CommentFound> getCommentById(Integer id) {
        return commentFoundMapper.getCommentById(id);
    }
}
