package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.CategoryAdminMapper;
import com.demo.lost_found.mapper.CommentLostMapper;
import com.demo.lost_found.mapper.GoodsLostMapper;
import com.demo.lost_found.mapper.LostAndReportMapper;
import com.demo.lost_found.pojo.*;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.pojo.form.LostAndReportAddForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.CommentLostService;
import com.demo.lost_found.service.LostAndReportService;
import com.demo.lost_found.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostAndReportServiceImpl implements LostAndReportService {

    @Autowired
    private LostAndReportMapper lostAndReportMapper;

    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    @Autowired
    private GoodsLostMapper goodsLostMapper;

    @Autowired
    private CommentLostService commentLostService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private CommentLostMapper commentLostMapper;

    @Override
    public BaseResponse<List<LostAndReport>> getList() {
        List<LostAndReport> list = lostAndReportMapper.getList();
        for (LostAndReport item : list) {
            item.setCategory(categoryAdminMapper.getById(item.getCategoryId()).getCategoryName());
            item.setImageUrl(goodsLostMapper.getById(item.getId()));
        }
        return new BaseResponse<>(200, "获取成功", list);

    }

    @Override
    public BaseResponse<String> add(LostAndReportAddForm lostAndReportAddForm) {
        LostAndReport lostAndReport = new LostAndReport();
        lostAndReport.setName(lostAndReportAddForm.getName());
        lostAndReport.setCategoryId(lostAndReportAddForm.getCategoryId());
        lostAndReport.setLostAddress(lostAndReportAddForm.getLostAddress());
        lostAndReport.setLostTime(lostAndReportAddForm.getLostTime());
        lostAndReport.setDetail(lostAndReportAddForm.getDetail());
        User user = UserContext.getCurrentUser();
        lostAndReport.setUserId(user.getId());
        lostAndReport.setUsername(user.getUsername());
        for (String s : lostAndReportAddForm.getContactWay()) {
            if ("0".equals(s)) {
                lostAndReport.setPhone(user.getPhone());
            } else if ("1".equals(s)) {
                lostAndReport.setWechatId(user.getWechatId());
            }
        }
        lostAndReport.setStatus("未找回");
        lostAndReport.setCheckStatus("待审核");
        lostAndReport.setCreatedAt(DateUtil.now());
        lostAndReportMapper.add(lostAndReport);
        // 类别下的物品数量加1
        categoryAdminMapper.incr(lostAndReport.getCategoryId());
        // 添加图片
        goodsLostMapper.add(lostAndReport.getId(), lostAndReportAddForm.getImageUrl());
        return new BaseResponse<>(200, "发布成功，等待管理员审核，可前往个人中心查看审核情况", null);
    }

    @Override
    public BaseResponse<LostAndReport> getById(Integer id) {
        LostAndReport data = lostAndReportMapper.getById(id);
        data.setCategory(categoryAdminMapper.getById(data.getCategoryId()).getCategoryName());
        data.setImageUrl(goodsLostMapper.getById(data.getId()));
        return new BaseResponse<>(200, "获取成功", data);
    }

    @Override
    public BaseResponse<String> mark(Integer id) {
        lostAndReportMapper.mark(id);
        return new BaseResponse<>(200, "已标记", null);
    }

    @Override
    public BaseResponse<List<CommentLost>> getCommentById(Integer id) {
        // 获取评论列表
        List<CommentLost> list = commentLostService.getCommentById(id);
        // 查询用户名和头像
        list.forEach(e -> {
            User user = userAdminService.getUserInfoById(e.getUserId()).getData();
            e.setUsername(user.getUsername());
            e.setAvatar(user.getAvatar());
        });
        return new BaseResponse<>(200, "获取成功", list);
    }

    @Override
    public BaseResponse<String> submitComment(Integer id, String comment) {
        Integer userId = UserContext.getCurrentUser().getId();
        commentLostMapper.add(id, userId, comment);
        return new BaseResponse<>(200, "发布成功", null);
    }
}
