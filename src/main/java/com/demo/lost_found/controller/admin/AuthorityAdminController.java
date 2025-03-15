package com.demo.lost_found.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.pojo.Authority;
import com.demo.lost_found.pojo.CarouselImages;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.AuthorityForm;
import com.demo.lost_found.pojo.form.UserAdminForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.AuthorityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
     * 获取所有权限列表，可以根据url或描述进行模糊搜索，可以根据是否启用进行筛选
     * @return
     */
    @PostMapping("/getAll")
    public BaseResponse<List<Authority>> getAll(@RequestBody AuthorityForm authorityForm) {
        List<Authority> list = authorityAdminService.getAll(authorityForm);
        return new BaseResponse<>(200, "搜索成功", list);
    }

    /**
     * 获取启用的权限列表（过滤器用）
     * @return
     */
    @GetMapping("/getEnable")
    public BaseResponse<List<Authority>> getEnable() {
        List<Authority> list = authorityAdminService.getEnable();
        return new BaseResponse<>(200, "获取成功", list);
    }

    /**
     * 启用禁用权限
     */
    @GetMapping("/enable")
    public BaseResponse enable(@RequestParam("id") Integer id, @RequestParam("enable") Boolean enable) {
        String enable1;
        if (enable) {
            enable1 = "true";
        } else {
            enable1 = "false";
        }
        return authorityAdminService.enable(id, enable1);
    }

    /**
     * 数据导出
     * @return
     */
    @PostMapping("/export")
    public void export(@RequestBody AuthorityForm authorityForm, HttpServletResponse httpServletResponse) throws IOException {
        List<Authority> authorities = authorityAdminService.getAuthorityList(authorityForm);
        authorityAdminService.export(authorities, httpServletResponse);
    }

    /**
     * 新增权限
     */
    @PostMapping("/addAuthority")
    public BaseResponse<String> addAuthority(@RequestBody Authority authority) {
        return authorityAdminService.addAuthority(authority);
    }

    /**
     * 删除权限
     */
    @GetMapping("/delete")
    public BaseResponse<String> delete(@RequestParam("id") Integer id) {
        return authorityAdminService.delete(id);
    }

    /**
     * 修改权限
     */
    @PostMapping("/update")
    public BaseResponse update(@RequestBody Authority authority) {
        authorityAdminService.update(authority);
        return new BaseResponse(200, "修改成功", null);
    }

}
