package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.LostAndReport;
import com.demo.lost_found.pojo.form.ReviewForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.LostAndFoundService;
import com.demo.lost_found.service.LostAndReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/lostAndReport")
public class LostAndReportAdminController {

    @Autowired
    private LostAndReportService lostAndReportService;

    /**
     * 根据物品名称、状态、审核状态查询物品挂失信息，并按创建时间倒序排序
     * @return
     */
    @PostMapping("/getAll")
    public BaseResponse<List<LostAndReport>> getAll(@RequestBody LostAndReport lostAndReport){
        return lostAndReportService.getAll(lostAndReport);
    }

    /**
     * 修改物品名称、物品类别、详细描述
     * @return
     */
    @PostMapping("/update")
    public BaseResponse update(@RequestBody LostAndReport lostAndReport){
        return lostAndReportService.update(lostAndReport);
    }

    /**
     * 删除物品挂失信息
     * @return
     */
    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam("id") Integer id){
        return lostAndReportService.delete(id);
    }

    /**
     * 审核物品挂失信息
     * @return
     */
    @PostMapping("/review")
    public BaseResponse review(@RequestBody ReviewForm reviewForm){
        return lostAndReportService.review(reviewForm);
    }
}
