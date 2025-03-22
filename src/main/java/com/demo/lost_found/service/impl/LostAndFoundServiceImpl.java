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
import com.demo.lost_found.pojo.form.LostAndFoundAddForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.CommentFoundService;
import com.demo.lost_found.service.LostAndFoundService;
import com.demo.lost_found.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        commentFoundMapper.add(id, userId, comment);
        return new BaseResponse<>(200, "发布成功", null);
    }
}


















