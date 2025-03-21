package com.demo.lost_found.service;

import com.demo.lost_found.pojo.Category;
import com.demo.lost_found.rep.BaseResponse;

import java.util.List;

public interface CategoryAdminService {
    BaseResponse<List<Category>> getList(String type);

    BaseResponse<String> add(Category category);

    BaseResponse<String> update(Category category);

    BaseResponse<String> delete(Integer id);
}
