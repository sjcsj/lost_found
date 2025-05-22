package com.demo.lost_found.controller.user;

import com.demo.lost_found.pojo.CommentFound;
import com.demo.lost_found.pojo.CommentLost;
import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.LostAndReport;
import com.demo.lost_found.pojo.form.LostAndFoundAddForm;
import com.demo.lost_found.pojo.form.LostAndReportAddForm;
import com.demo.lost_found.pojo.vo.LostAndFoundVO;
import com.demo.lost_found.pojo.vo.LostAndReportVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.LostAndReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/lostAndReport")
public class LostAndReportController {

    @Autowired
    private LostAndReportService lostAndReportService;

    /**
     * 发布物品挂失信息
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<String> add(@RequestBody LostAndReportAddForm lostAndReportAddForm){
        return lostAndReportService.add(lostAndReportAddForm);
    }

    /**
     * 获取物品挂失信息列表，按丢失时间倒序排序，只返回审核通过的
     * @return
     */
    @GetMapping("/getList")
    public BaseResponse<List<LostAndReport>> getList() {
        return lostAndReportService.getList();
    }

    /**
     * 根据id查询详细的物品挂失信息
     */
    @GetMapping("/getById")
    public BaseResponse<LostAndReport> getById(@RequestParam("id") Integer id) {
        return lostAndReportService.getById(id);
    }

    /**
     * 将对应id的物品挂失信息标记为已找回
     */
    @GetMapping("/mark")
    public BaseResponse<String> mark(@RequestParam("id") Integer id) {
        return lostAndReportService.mark(id);
    }

    /**
     * 根据物品挂失信息id获取对应的评论
     */
    @GetMapping("/getCommentById")
    public BaseResponse<List<CommentLost>> getCommentById(@RequestParam("id") Integer id) {
        return lostAndReportService.getCommentById(id);
    }

    /**
     * 添加评论
     */
    @GetMapping("/submitComment")
    public BaseResponse<String> submitComment(@RequestParam("id") Integer id, @RequestParam("comment") String comment) {
        return lostAndReportService.submitComment(id, comment);
    }

    /**
     * 获取当前用户的物品挂失信息，包括通过、未通过、待审核
     */
    @GetMapping("/getAllOfCurrentUser")
    public BaseResponse<LostAndReportVO> getAllOfCurrentUser() {
        return lostAndReportService.getAllOfCurrentUser();
    }

}
