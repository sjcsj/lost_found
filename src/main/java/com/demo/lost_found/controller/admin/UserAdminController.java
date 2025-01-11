package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.UserAdminForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 * 后台相关接口必须以/admin开头
 */
// TODO 只剩拉黑功能没有完成
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

    @Autowired
    private UserAdminService userAdminService;

    /**
     * 精确搜索，模糊搜索
     * @return
     */
    @PostMapping("/getUserList")
    public BaseResponse<List<User>> getUserList(@RequestBody UserAdminForm userAdminForm) {
        List<User> users = userAdminService.getUserList(userAdminForm);
        return new BaseResponse<>(200, "搜索成功", users);
    }

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/getUserInfoById")
    public BaseResponse<User> getUserInfoById(@RequestParam("id") Integer id) {
        return userAdminService.getUserInfoById(id);
    }

    /**
     * 新增用户
     */
    @PostMapping("/addUser")
    public BaseResponse<String> addUser(@RequestBody User user) {
        return userAdminService.addUser(user);
    }

    /**
     * 根据id列表删除指定的用户
     */
    @GetMapping("/deleteByIds")
    public BaseResponse<String> deleteByIds(@RequestParam("ids") String ids) {
        // 前端传过来时必然不是空列表
        // 将逗号分隔的字符串拆分成数组并转换为 List<Integer>
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        userAdminService.deleteByIds(idList);
        return new BaseResponse<>(200, "删除成功", null);
    }

    /**
     * 根据用户id修改用户
     */
    @PostMapping("/editUser")
    public BaseResponse<String> editUser(@RequestBody User user) {
        return userAdminService.editUser(user);
    }

    /**
     * 重置密码
     */
    @PostMapping("/reSetPassword")
    public BaseResponse<String> reSetPassword(@RequestBody User user) {
        return userAdminService.reSetPassword(user);
    }

    /**
     * 数据导出
     * @return
     */
    @PostMapping("/export")
    public void export(@RequestBody UserAdminForm userAdminForm, HttpServletResponse httpServletResponse) throws IOException {
        List<User> users = userAdminService.getUserList(userAdminForm);
        userAdminService.export(users, httpServletResponse);
    }


}
