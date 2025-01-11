package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.demo.lost_found.contants.RoleConstants;
import com.demo.lost_found.mapper.UserAdminMapper;
import com.demo.lost_found.mapper.UserMapper;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.UserAdminForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.UserAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.demo.lost_found.utils.MD5Util.md5Lower;

@Service
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {


    @Autowired
    private UserAdminMapper userAdminMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserList(UserAdminForm userAdminForm) {
        List<User> list = new ArrayList<>();
        if (userAdminForm.getSelectType() == 1) {
            // 精确搜索
            list = userAdminMapper.getUserListExact(userAdminForm);
        } else {
            // 模糊搜索
            list = userAdminMapper.getUserListFuzzy(userAdminForm);
        }
        // 将英文换中文
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            user.setRole(RoleConstants.map.get(user.getRole()));
            list.set(i, user);
        }
        return list;
    }

    @Override
    public BaseResponse<User> getUserInfoById(Integer id) {
        User user = userAdminMapper.getUserInfoById(id);
        return new BaseResponse<>(200, "获取成功", user);
    }

    @Override
    public BaseResponse<String> addUser(User user) {
        // 判断用户名是否唯一，不能和普通用户管理员重名，也不能跟已删除的用户重名
        User user1 = userMapper.selectByUsernameAllRole(user.getUsername());
        if (user1 != null) {
            // 跟其他用户名冲突
            return new BaseResponse(400, "用户名已被他人使用，请更换其他用户名", null);
        }
        // 唯一，加密密码，添加基础信息后入库
        user.setPassword(md5Lower(user.getPassword()));
        user.setCreatedAt(DateUtil.now());
        userMapper.addUser(user);
        return new BaseResponse(200, "新增成功", null);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        // 注意是逻辑删除
        // 先查出ids的所有用户
        List<User> users = userAdminMapper.selectByIds(ids);
        // 添加删除时间并更新
        String now = DateUtil.now();
        users.forEach(i -> i.setDeletedAt(now));
        users.forEach(i -> userAdminMapper.updateById(i));
    }

    @Override
    public void export(List<User> users, HttpServletResponse httpServletResponse) throws IOException {
        // 设置响应头
        httpServletResponse.setContentType("application/vnd.ms-excel");
        // 处理文件名编码问题，防止中文乱码
        String fileName = "用户列表.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName);
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream();){
            // 写入 Excel 文件
            EasyExcel.write(outputStream, User.class)
                    .sheet("用户列表")
                    .doWrite(users);
        }
        log.info("用户列表导出成功");
    }

    @Override
    public BaseResponse<String> editUser(User user) {
        // 将“普通用户”改为user，设置更新时间
        user.setRole(RoleConstants.USER);
        user.setUpdatedAt(DateUtil.now());
        userAdminMapper.updateById(user);
        return new BaseResponse<>(200, "修改成功", null);
    }

    @Override
    public BaseResponse<String> reSetPassword(User user) {
        String newPassword = "1234";
        user.setPassword(md5Lower(newPassword));
        user.setRole(RoleConstants.USER);
        user.setUpdatedAt(DateUtil.now());
        userAdminMapper.updateById(user);
        return new BaseResponse<>(200, "", newPassword);
    }

}
