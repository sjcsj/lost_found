package com.demo.lost_found.pojo;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String wechatId;
    private String role;
    private String gender;
    private String avatar;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
