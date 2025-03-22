package com.demo.lost_found.pojo.form;

import lombok.Data;

/**
 * 发布失物招领信息
 */
@Data
public class LostAndFoundAddForm {

    private String name;
    private Integer categoryId;
    private String pickupAddress;
    private String pickupTime;
    private String detail;
    private String[] contactWay;
    private String imageUrl;

}
