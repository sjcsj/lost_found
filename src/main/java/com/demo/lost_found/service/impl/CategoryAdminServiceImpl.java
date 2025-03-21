package com.demo.lost_found.service.impl;

import com.demo.lost_found.mapper.CategoryAdminMapper;
import com.demo.lost_found.pojo.CarouselImages;
import com.demo.lost_found.pojo.Category;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.CategoryAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryAdminServiceImpl implements CategoryAdminService {

    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    @Override
    public BaseResponse<List<Category>> getList(String type) {
        List<Category> list = new ArrayList<>();
        if ("1".equals(type)) {
            // 按排序字段排序
            list = categoryAdminMapper.getListBySort();
        } else if ("2".equals(type)) {
            // 按点击次数排序
            list = categoryAdminMapper.getListByClicks();
        }
        return new BaseResponse<>(200, "查询成功", list);
    }

    @Override
    public BaseResponse<String> add(Category category) {
        category.setCount(0);
        categoryAdminMapper.add(category);
        return new BaseResponse<>(200, "新增成功", null);
    }

    @Override
    public BaseResponse<String> update(Category category) {
        categoryAdminMapper.update(category);
        return new BaseResponse<>(200, "修改成功", null);
    }

    @Override
    public BaseResponse<String> delete(Integer id) {
        categoryAdminMapper.delete(id);
        return new BaseResponse<>(200, "删除成功", null);
    }
}
