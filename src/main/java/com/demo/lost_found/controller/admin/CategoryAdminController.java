package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.CarouselImages;
import com.demo.lost_found.pojo.Category;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.CategoryAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryAdminController {

    @Autowired
    private CategoryAdminService categoryAdminService;

    /**
     * 后台获取类别列表，type为1是按排序字段排序，type为2是按物品数量排序
     * @return
     */
    @GetMapping("/getList")
    public BaseResponse<List<Category>> getList(@RequestParam("type") String type) {
        return categoryAdminService.getList(type);
    }

    @PostMapping("/add")
    public BaseResponse<String> add(@RequestBody Category category) {
        return categoryAdminService.add(category);
    }

    @PostMapping("/update")
    public BaseResponse<String> update(@RequestBody Category category) {
        return categoryAdminService.update(category);
    }

    @GetMapping("/delete")
    public BaseResponse<String> delete(@RequestParam("id") Integer id) {
        return categoryAdminService.delete(id);
    }
}
