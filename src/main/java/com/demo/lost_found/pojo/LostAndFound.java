package com.demo.lost_found.pojo;

import lombok.Data;

@Data
public class LostAndFound {

    private Integer id;
    private String name;
    private Integer categoryId;
    private String pickupAddress;
    private String pickupTime;
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
