package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.demo.lost_found.contants.BlackStatusConstants;
import com.demo.lost_found.contants.RoleConstants;
import com.demo.lost_found.mapper.BlackListMapper;
import com.demo.lost_found.mapper.UserAdminMapper;
import com.demo.lost_found.pojo.BlackList;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.pojo.form.BlackInfoForm;
import com.demo.lost_found.pojo.form.BlackListAdminForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class BlackListServiceImpl implements BlackListService {

    @Autowired
    private BlackListMapper blackListMapper;

    @Autowired
    private UserAdminMapper userAdminMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<BlackList> getBlackList(BlackListAdminForm blackListAdminForm) {
        String username = blackListAdminForm.getUsername();
        String adminname = blackListAdminForm.getAdminname();
        List<Integer> userIds = new ArrayList<>();
        List<Integer> adminIds = new ArrayList<>();
        List<BlackList> list = new ArrayList<>();
        if (!StringUtils.isEmpty(username)) {
            if (blackListAdminForm.getSelectType() == 1) {
                userIds = userAdminMapper.selectByUsernameExact(username);
            } else {
                userIds = userAdminMapper.selectByUsernameFuzzy(username);
            }
            // 如果没有查询到用户id列表的话，后面的逻辑也都不用走了
            if (CollectionUtils.isEmpty(userIds)) {
                return list;
            }
        }
        if (!StringUtils.isEmpty(adminname)) {
            if (blackListAdminForm.getSelectType() == 1) {
                adminIds = userAdminMapper.selectByUsernameExact(adminname);
            } else {
                adminIds = userAdminMapper.selectByUsernameFuzzy(adminname);
            }
            // 如果没有查询到管理员id列表的话，后面的逻辑也都不用走了
            if (CollectionUtils.isEmpty(adminIds)) {
                return list;
            }
        }
        // 精确搜索和模糊搜索都可使用该sql
        list = blackListMapper.getBlackList(userIds, adminIds, blackListAdminForm.getStatus());
        for (int i = 0; i < list.size(); i++) {
            BlackList item = list.get(i);
            item.setUsername(userAdminMapper.getUsernameById(item.getUserId()));
            item.setAdminname(userAdminMapper.getUsernameById(item.getAdminId()));
            item.setStatusName(BlackStatusConstants.map.get(item.getStatus()));
        }
        return list;
    }

    @Override
    public BaseResponse<BlackList> getUserStatus(Integer userId) {
        BlackList blackList = blackListMapper.getUserStatus(userId);
        if (blackList != null) {
            // 检查该记录是否已经到达释放时间了
            String deadline = blackList.getDeadline();
            String now = DateUtil.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 转换成 LocalDateTime 对象
            LocalDateTime deadline1 = LocalDateTime.parse(deadline, formatter);
            LocalDateTime now1 = LocalDateTime.parse(now, formatter);
            if (deadline1.isBefore(now1)) {
                // 已经到达释放时间，更新黑名单记录
                blackList.setStatus(2);
                blackListMapper.updateById(blackList);
                return new BaseResponse<>(200, "当前不处于拉黑中", null);
            } else {
                // 没有到达释放时间，返回拉黑记录
                return new BaseResponse<>(400, "当前处于拉黑中", blackList);
            }
        }
        // 当前该用户不处于拉黑中状态
        return new BaseResponse<>(200, "当前不处于拉黑中", null);
    }

    @Override
    public BaseResponse<String> addBlackInfo(BlackInfoForm blackInfoForm) {
        // 校验deadline是否在当前时间之后
        String deadline = blackInfoForm.getDeadline();
        String now = DateUtil.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 转换成 LocalDateTime 对象
        LocalDateTime deadline1 = LocalDateTime.parse(deadline, formatter);
        LocalDateTime now1 = LocalDateTime.parse(now, formatter);
        if (deadline1.isBefore(now1)) {
            return new BaseResponse<>(400, "请将释放时间设置为当前时间之后", null);
        }
        // 校验通过，入库
        // 获取管理员id
        Integer adminId = UserContext.getCurrentUser().getId();
        BlackList blackList = new BlackList();
        BeanUtils.copyProperties(blackInfoForm, blackList);
        blackList.setAdminId(adminId);
        blackList.setStatus(1);
        blackList.setCreatedAt(now);
        blackListMapper.insertInfo(blackList);
        // 将对应用户的token清除
        Set<String> keys = stringRedisTemplate.keys("user:token:*");
        if (keys != null && !keys.isEmpty()) {
            // 遍历这些键并检查它们的值
            for (String key : keys) {
                String userId = stringRedisTemplate.opsForValue().get(key);
                if (userId != null && userId.equals(blackInfoForm.getUserId() + "")) {
                    // 如果找到与 userId 匹配的 token，则删除这个键
                    stringRedisTemplate.delete(key);
                }
            }
        }
        return new BaseResponse<>(200, "拉黑成功", null);
    }

    @Override
    public void export(List<BlackList> blackList, HttpServletResponse httpServletResponse) throws IOException {
        // 设置响应头
        httpServletResponse.setContentType("application/vnd.ms-excel");
        // 处理文件名编码问题，防止中文乱码
        String fileName = "黑名单列表.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName);
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream()){
            // 写入 Excel 文件
            EasyExcel.write(outputStream, BlackList.class)
                    .sheet("黑名单列表")
                    .doWrite(blackList);
        }
        log.info("黑名单列表导出成功");
    }

    @Override
    public BaseResponse<String> releaseUserById(Integer id) {
        String now = DateUtil.now();
        blackListMapper.releaseUserById(id, now);
        return new BaseResponse<>(200, "释放成功", null);
    }

    @Override
    public BaseResponse<String> updateBlackInfo(BlackList blackList) {
        // 根据释放时间判断是否修改拉黑状态
        String deadline = blackList.getDeadline();
        String now = DateUtil.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 转换成 LocalDateTime 对象
        LocalDateTime deadline1 = LocalDateTime.parse(deadline, formatter);
        LocalDateTime now1 = LocalDateTime.parse(now, formatter);
        if (deadline1.isBefore(now1)) {
            blackList.setStatus(2);
        }
        blackList.setUpdatedAt(now);
        blackListMapper.updateById(blackList);
        return new BaseResponse<>(200, "修改成功", null);
    }

    @Override
    public BaseResponse<List<BlackList>> getBlackHistory(Integer id) {
        List<BlackList> blackLists = blackListMapper.selectHistoryByUserId(id);
        for (int i = 0; i < blackLists.size(); i++) {
            BlackList item = blackLists.get(i);
            item.setUsername(userAdminMapper.getUsernameById(item.getUserId()));
            item.setAdminname(userAdminMapper.getUsernameById(item.getAdminId()));
            item.setStatusName(BlackStatusConstants.map.get(item.getStatus()));
        }
        return new BaseResponse<>(200, "查询成功", blackLists);
    }
}
