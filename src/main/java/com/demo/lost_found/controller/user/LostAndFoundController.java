package com.demo.lost_found.controller.user;

import com.demo.lost_found.pojo.CommentFound;
import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.form.LostAndFoundAddForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.CommentFoundService;
import com.demo.lost_found.service.LostAndFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.List;

@RestController
@RequestMapping("/user/lostAndFound")
public class LostAndFoundController {

    @Autowired
    private LostAndFoundService lostAndFoundService;

    /**
     * 发布失物招领信息
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<String> add(@RequestBody LostAndFoundAddForm lostAndFoundAddForm){
        return lostAndFoundService.add(lostAndFoundAddForm);
    }

    /**
     * 获取失物招领信息列表，按捡拾时间倒序排序，只返回审核通过的
     * @return
     */
    @GetMapping("/getList")
    public BaseResponse<List<LostAndFound>> getList() {
        return lostAndFoundService.getList();
    }

    /**
     * 根据id查询详细的失物招领信息
     */
    @GetMapping("/getById")
    public BaseResponse<LostAndFound> getById(@RequestParam("id") Integer id) {
        return lostAndFoundService.getById(id);
    }

    /**
     * 将对应id的失物招领信息标记为已认领
     */
    @GetMapping("/mark")
    public BaseResponse<String> mark(@RequestParam("id") Integer id) {
        return lostAndFoundService.mark(id);
    }

    /**
     * 根据失物招领信息id获取对应的评论
     */
    @GetMapping("/getCommentById")
    public BaseResponse<List<CommentFound>> getCommentById(@RequestParam("id") Integer id) {
        return lostAndFoundService.getCommentById(id);
    }

    /**
     * 添加评论
     */
    @GetMapping("/submitComment")
    public BaseResponse<String> submitComment(@RequestParam("id") Integer id, @RequestParam("comment") String comment) {
        return lostAndFoundService.submitComment(id, comment);
    }
}





















