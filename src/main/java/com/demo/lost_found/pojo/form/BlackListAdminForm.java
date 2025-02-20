package com.demo.lost_found.pojo.form;

import lombok.Data;

/**
 * 后台管理-黑名单管理，接收相关参数
 */
@Data
public class BlackListAdminForm {

    private String username;

    private String adminname;

    // 拉黑状态，1:拉黑中，2:已释放
    private Integer status;

    // 搜索类型，1:精确搜索  2:模糊搜索
    private Integer selectType;
}
