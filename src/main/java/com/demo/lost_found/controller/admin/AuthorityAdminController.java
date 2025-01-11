package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.Authority;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.AuthorityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理
 */
@RestController
@RequestMapping("/admin/authority")
public class AuthorityAdminController {

    @Autowired
    private AuthorityAdminService authorityAdminService;

    /**
     * 获取权限列表
     * @return
     */
    @GetMapping("getAll")
    public BaseResponse<List<Authority>> getAll() {
        List<Authority> list = authorityAdminService.getAll();
        return new BaseResponse<>(200, "获取成功", list);
    }
}
