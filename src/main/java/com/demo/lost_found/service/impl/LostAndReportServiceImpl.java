package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.CategoryAdminMapper;
import com.demo.lost_found.mapper.CommentLostMapper;
import com.demo.lost_found.mapper.GoodsLostMapper;
import com.demo.lost_found.mapper.LostAndReportMapper;
import com.demo.lost_found.pojo.*;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.pojo.form.BlackInfoForm;
import com.demo.lost_found.pojo.form.LostAndReportAddForm;
import com.demo.lost_found.pojo.form.ReviewForm;
import com.demo.lost_found.pojo.vo.LostAndFoundVO;
import com.demo.lost_found.pojo.vo.LostAndReportVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.BlackListService;
import com.demo.lost_found.service.CommentLostService;
import com.demo.lost_found.service.LostAndReportService;
import com.demo.lost_found.service.UserAdminService;
import com.demo.lost_found.utils.BaiduUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private BaiduUtil baiduUtil;

    @Autowired
    private BlackListService blackListService;

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
        // 验证评论是否合规
        boolean isOK = baiduUtil.textDetection(comment);
        if (!isOK) {
            // 不合规，封禁该用户30分钟
            BlackInfoForm blackInfoForm = new BlackInfoForm();
            blackInfoForm.setUserId(userId);
            Date date = DateUtil.offsetMinute(new Date(), 30);
            String formattedTime = DateUtil.formatDateTime(date);
            blackInfoForm.setDeadline(formattedTime);
            blackInfoForm.setReason("发布不当评论");
            blackInfoForm.setNotes("发布评论前请仔细确认是否恰当");
            blackListService.addBlackInfo(blackInfoForm);
            return new BaseResponse<>(400, "发布失败", null);
        }
        commentLostMapper.add(id, userId, comment);
        return new BaseResponse<>(200, "发布成功", null);
    }

    @Override
    public BaseResponse<List<LostAndReport>> getAll(LostAndReport lostAndReport) {
        List<LostAndReport> list = lostAndReportMapper.getAll(lostAndReport);
        for (LostAndReport item : list) {
            // 查询物品类别
            item.setCategory(categoryAdminMapper.getById(item.getCategoryId()).getCategoryName());
            // 查询物品图片
            item.setImageUrl(goodsLostMapper.getById(item.getId()));
        }
        return new BaseResponse<>(200, "获取成功", list);
    }

    @Override
    public BaseResponse update(LostAndReport lostAndReport) {
        // 原类别数量减一，修改后的类别数量加一
        Integer categoryId = lostAndReportMapper.getCategoryIdById(lostAndReport.getId());
        categoryAdminMapper.decr(categoryId);
        lostAndReportMapper.update(lostAndReport);
        categoryAdminMapper.incr(lostAndReport.getCategoryId());
        return new BaseResponse(200, "修改成功", null);
    }

    @Override
    public BaseResponse delete(Integer id) {
        // 类别数量减一
        Integer categoryId = lostAndReportMapper.getCategoryIdById(id);
        categoryAdminMapper.decr(categoryId);
        // 评论删除
        commentLostService.delete(id);
        // 图片删除
        goodsLostMapper.delete(id);
        // 删除失物招领信息
        lostAndReportMapper.deleteById(id);
        return new BaseResponse(200, "删除成功", null);
    }

    @Override
    public BaseResponse review(ReviewForm reviewForm) {
        // 修改审核状态
        lostAndReportMapper.review(reviewForm);
        return new BaseResponse(200, "审核完成", null);
    }

    @Override
    public BaseResponse<LostAndReportVO> getAllOfCurrentUser() {
        User user = UserContext.getCurrentUser();
        // 获取当前用户的所有物品挂失信息
        List<LostAndReport> list = lostAndReportMapper.getAllByUserId(user.getId());
        // 填充图片和类别
        for (LostAndReport item : list) {
            // 查询物品类别
            item.setCategory(categoryAdminMapper.getById(item.getCategoryId()).getCategoryName());
            // 查询物品图片
            item.setImageUrl(goodsLostMapper.getById(item.getId()));
        }
        // 分成三部分
        LostAndReportVO lostAndReportVO = new LostAndReportVO();
        for (LostAndReport item : list) {
            if ("通过".equals(item.getCheckStatus())) {
                lostAndReportVO.approved.add(item);
            } else if ("不通过".equals(item.getCheckStatus())) {
                lostAndReportVO.rejected.add(item);
            } else if ("待审核".equals(item.getCheckStatus())) {
                lostAndReportVO.pending.add(item);
            }
        }
        return new BaseResponse<>(200, "获取成功", lostAndReportVO);
    }
}
