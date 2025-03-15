package com.demo.lost_found.service;

import com.demo.lost_found.pojo.Authority;
import com.demo.lost_found.pojo.form.AuthorityForm;
import com.demo.lost_found.rep.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AuthorityAdminService {
    List<Authority> getAll(AuthorityForm authorityForm);

    List<Authority> getAuthorityList(AuthorityForm authorityForm);

    List<Authority> getEnable();

    BaseResponse enable(Integer id, String enable);

    void export(List<Authority> authorities, HttpServletResponse httpServletResponse) throws IOException;

    BaseResponse<String> addAuthority(Authority authority);

    BaseResponse<String> delete(Integer id);

    void update(Authority authority);
}