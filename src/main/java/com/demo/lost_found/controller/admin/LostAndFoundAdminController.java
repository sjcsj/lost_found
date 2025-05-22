package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.form.LostAndFoundAddForm;
import com.demo.lost_found.pojo.form.ReviewForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.LostAndFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/lostAndFound")
public class LostAndFoundAdminController {

    @Autowired
    private LostAndFoundService lostAndFoundService;

    /**
     * 根据物品名称、状态、审核状态查询失物招领信息，并按创建时间倒序排序
     * @return
     */
    @PostMapping("/getAll")
    public BaseResponse<List<LostAndFound>> getAll(@RequestBody LostAndFound lostAndFound){
        return lostAndFoundService.getAll(lostAndFound);
    }

    /**
     * 修改物品名称、物品类别、详细描述
     * @return
     */
    @PostMapping("/update")
    public BaseResponse update(@RequestBody LostAndFound lostAndFound){
        return lostAndFoundService.update(lostAndFound);
    }

    /**
     * 删除失物招领信息
     * @return
     */
    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam("id") Integer id){
        return lostAndFoundService.delete(id);
    }

    /**
     * 审核失物招领信息
     * @return
     */
    @PostMapping("/review")
    public BaseResponse review(@RequestBody ReviewForm reviewForm){
        return lostAndFoundService.review(reviewForm);
    }
}
