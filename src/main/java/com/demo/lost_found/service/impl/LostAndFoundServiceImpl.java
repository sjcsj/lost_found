package com.demo.lost_found.service.impl;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.CategoryAdminMapper;
import com.demo.lost_found.mapper.CommentFoundMapper;
import com.demo.lost_found.mapper.GoodsFoundMapper;
import com.demo.lost_found.mapper.LostAndFoundMapper;
import com.demo.lost_found.pojo.CommentFound;
import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.pojo.form.BlackInfoForm;
import com.demo.lost_found.pojo.form.LostAndFoundAddForm;
import com.demo.lost_found.pojo.form.ReviewForm;
import com.demo.lost_found.pojo.vo.LostAndFoundVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.BlackListService;
import com.demo.lost_found.service.CommentFoundService;
import com.demo.lost_found.service.LostAndFoundService;
import com.demo.lost_found.service.UserAdminService;
import com.demo.lost_found.utils.BaiduUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LostAndFoundServiceImpl implements LostAndFoundService {

    @Autowired
    private LostAndFoundMapper lostAndFoundMapper;

    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    @Autowired
    private GoodsFoundMapper goodsFoundMapper;

    @Autowired
    private CommentFoundService commentFoundService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private CommentFoundMapper commentFoundMapper;

    @Autowired
    private BaiduUtil baiduUtil;

    @Autowired
    private BlackListService blackListService;

    @Override
    public BaseResponse<String> add(LostAndFoundAddForm lostAndFoundAddForm) {
        LostAndFound lostAndFound = new LostAndFound();
        lostAndFound.setName(lostAndFoundAddForm.getName());
        lostAndFound.setCategoryId(lostAndFoundAddForm.getCategoryId());
        lostAndFound.setPickupAddress(lostAndFoundAddForm.getPickupAddress());
        lostAndFound.setPickupTime(lostAndFoundAddForm.getPickupTime());
        lostAndFound.setDetail(lostAndFoundAddForm.getDetail());
        User user = UserContext.getCurrentUser();
        lostAndFound.setUserId(user.getId());
        lostAndFound.setUsername(user.getUsername());
        for (String s : lostAndFoundAddForm.getContactWay()) {
            if ("0".equals(s)) {
                lostAndFound.setPhone(user.getPhone());
            } else if ("1".equals(s)) {
                lostAndFound.setWechatId(user.getWechatId());
            }
        }
        lostAndFound.setStatus("未认领");
        lostAndFound.setCheckStatus("待审核");
        lostAndFound.setCreatedAt(DateUtil.now());
        lostAndFoundMapper.add(lostAndFound);
        // 类别下的物品数量加1
        categoryAdminMapper.incr(lostAndFound.getCategoryId());
        // 添加图片
        goodsFoundMapper.add(lostAndFound.getId(), lostAndFoundAddForm.getImageUrl());
        return new BaseResponse<>(200, "发布成功，等待管理员审核，可前往个人中心查看审核情况", null);
    }

    @Override
    public BaseResponse<List<LostAndFound>> getList() {
        List<LostAndFound> list = lostAndFoundMapper.getList();
        for (LostAndFound item : list) {
            item.setCategory(categoryAdminMapper.getById(item.getCategoryId()).getCategoryName());
            item.setImageUrl(goodsFoundMapper.getById(item.getId()));
        }
        return new BaseResponse<>(200, "获取成功", list);
    }

    @Override
    public BaseResponse<LostAndFound> getById(Integer id) {
        LostAndFound data = lostAndFoundMapper.getById(id);
        data.setCategory(categoryAdminMapper.getById(data.getCategoryId()).getCategoryName());
        data.setImageUrl(goodsFoundMapper.getById(data.getId()));
        return new BaseResponse<>(200, "获取成功", data);
    }

    @Override
    public BaseResponse<String> mark(Integer id) {
        lostAndFoundMapper.mark(id);
        return new BaseResponse<>(200, "已标记", null);
    }

    @Override
    public BaseResponse<List<CommentFound>> getCommentById(Integer id) {
        // 获取评论列表
        List<CommentFound> list = commentFoundService.getCommentById(id);
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
        commentFoundMapper.add(id, userId, comment);
        return new BaseResponse<>(200, "发布成功", null);
    }

    @Override
    public BaseResponse<List<LostAndFound>> getAll(LostAndFound lostAndFound) {
        List<LostAndFound> list = lostAndFoundMapper.getAll(lostAndFound);
        for (LostAndFound item : list) {
            // 查询物品类别
            item.setCategory(categoryAdminMapper.getById(item.getCategoryId()).getCategoryName());
            // 查询物品图片
            item.setImageUrl(goodsFoundMapper.getById(item.getId()));
        }
        return new BaseResponse<>(200, "获取成功", list);
    }

    @Override
    public BaseResponse update(LostAndFound lostAndFound) {
        // 原类别数量减一，修改后的类别数量加一
        Integer categoryId = lostAndFoundMapper.getCategoryIdById(lostAndFound.getId());
        categoryAdminMapper.decr(categoryId);
        lostAndFoundMapper.update(lostAndFound);
        categoryAdminMapper.incr(lostAndFound.getCategoryId());
        return new BaseResponse(200, "修改成功", null);
    }

    @Override
    public BaseResponse delete(Integer id) {
        // 类别数量减一
        Integer categoryId = lostAndFoundMapper.getCategoryIdById(id);
        categoryAdminMapper.decr(categoryId);
        // 评论删除
        commentFoundService.delete(id);
        // 图片删除
        goodsFoundMapper.delete(id);
        // 删除失物招领信息
        lostAndFoundMapper.deleteById(id);
        return new BaseResponse(200, "删除成功", null);
    }

    @Override
    public BaseResponse review(ReviewForm reviewForm) {
        // 修改审核状态
        lostAndFoundMapper.review(reviewForm);
        return new BaseResponse(200, "审核完成", null);
    }

    @Override
    public BaseResponse<LostAndFoundVO> getAllOfCurrentUser() {
        User user = UserContext.getCurrentUser();
        // 获取当前用户的所有失物招领信息
        List<LostAndFound> list = lostAndFoundMapper.getAllByUserId(user.getId());
        // 填充图片和类别
        for (LostAndFound item : list) {
            // 查询物品类别
            item.setCategory(categoryAdminMapper.getById(item.getCategoryId()).getCategoryName());
            // 查询物品图片
            item.setImageUrl(goodsFoundMapper.getById(item.getId()));
        }
        // 分成三部分
        LostAndFoundVO lostAndFoundVO = new LostAndFoundVO();
        for (LostAndFound item : list) {
            if ("通过".equals(item.getCheckStatus())) {
                lostAndFoundVO.approved.add(item);
            } else if ("不通过".equals(item.getCheckStatus())) {
                lostAndFoundVO.rejected.add(item);
            } else if ("待审核".equals(item.getCheckStatus())) {
                lostAndFoundVO.pending.add(item);
            }
        }
        return new BaseResponse<>(200, "获取成功", lostAndFoundVO);
    }
}


















