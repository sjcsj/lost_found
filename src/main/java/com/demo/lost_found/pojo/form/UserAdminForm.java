package com.demo.lost_found.pojo.form;

import com.demo.lost_found.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * 后台管理-用户管理，接收相关参数
 */
@Data
public class UserAdminForm {

    private String username;

    private String phone;

    private String wechatId;

    private String gender;

    // 搜索类型，1:精确搜索  2:模糊搜索
    private Integer selectType;

}
