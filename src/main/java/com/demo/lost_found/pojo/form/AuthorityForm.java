package com.demo.lost_found.pojo.form;

import lombok.Data;

/**
 * 后台管理-权限管理，接收相关参数
 */
@Data
public class AuthorityForm {

    private String url;
    private String description;
    private String enable;
}
