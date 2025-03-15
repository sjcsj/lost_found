package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.demo.lost_found.mapper.AuthorityAdminMapper;
import com.demo.lost_found.pojo.Authority;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.AuthorityForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.AuthorityAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AuthorityAdminServiceImpl implements AuthorityAdminService {

    @Autowired
    private AuthorityAdminMapper authorityAdminMapper;

    @Override
    public List<Authority> getAll(AuthorityForm authorityForm) {
        List<Authority> list = authorityAdminMapper.getAll(authorityForm);
        list.forEach(e -> {
            Boolean a = "true".equals(e.getEnable()) ? true : false;
            e.setEnable1(a);
        });
        return list;
    }

    @Override
    public List<Authority> getAuthorityList(AuthorityForm authorityForm) {
        List<Authority> list = authorityAdminMapper.getAll(authorityForm);
        list.forEach(e -> {
            String a = "true".equals(e.getEnable()) ? "是" : "否";
            e.setEnable(a);
        });
        return list;
    }

    @Override
    public List<Authority> getEnable() {
        return authorityAdminMapper.getEnable();
    }

    @Override
    public BaseResponse enable(Integer id, String enable) {
        authorityAdminMapper.enable(id, enable);
        String s = enable.equals("true") ? "已启用" : "已禁用";
        return new BaseResponse(200, s, null);
    }

    @Override
    public void export(List<Authority> authorities, HttpServletResponse httpServletResponse) throws IOException {
        // 设置响应头
        httpServletResponse.setContentType("application/vnd.ms-excel");
        // 处理文件名编码问题，防止中文乱码
        String fileName = "权限列表.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName);
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream();){
            // 写入 Excel 文件
            EasyExcel.write(outputStream, Authority.class)
                    .sheet("权限列表")
                    .doWrite(authorities);
        }
        log.info("权限列表导出成功");
    }

    @Override
    public BaseResponse<String> addAuthority(Authority authority) {
        authorityAdminMapper.addAuthority(authority);
        return new BaseResponse<>(200, "新增成功", null);
    }

    @Override
    public BaseResponse<String> delete(Integer id) {
        String now = DateUtil.now();
        authorityAdminMapper.delete(id, now);
        return new BaseResponse<>(200, "删除成功", null);
    }

    @Override
    public void update(Authority authority) {
        authorityAdminMapper.update(authority);
    }
}
