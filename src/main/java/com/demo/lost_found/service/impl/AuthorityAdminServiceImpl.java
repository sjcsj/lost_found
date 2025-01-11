package com.demo.lost_found.service.impl;

import com.demo.lost_found.mapper.AuthorityAdminMapper;
import com.demo.lost_found.pojo.Authority;
import com.demo.lost_found.service.AuthorityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorityAdminServiceImpl implements AuthorityAdminService {

    @Autowired
    private AuthorityAdminMapper authorityAdminMapper;

    @Override
    public List<Authority> getAll() {
        return authorityAdminMapper.getAll();
    }
}
