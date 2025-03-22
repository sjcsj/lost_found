package com.demo.lost_found.pojo;

import lombok.Data;

@Data
public class LostAndReport {

    private Integer id;
    private String name;
    private Integer categoryId;
    private String lostAddress;
    private String lostTime;
    private String detail;
    private Integer userId;
    private String username;
    private String phone;
    private String wechatId;
    private String status;
    private String checkStatus;
    private String failureReason;
    private String createdAt;

    // 类别名称
    private String category;
    // 图片url
    private String imageUrl;
}
