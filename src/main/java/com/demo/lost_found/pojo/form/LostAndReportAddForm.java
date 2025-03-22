package com.demo.lost_found.pojo.form;

import lombok.Data;

@Data
public class LostAndReportAddForm {

    private String name;
    private Integer categoryId;
    private String lostAddress;
    private String lostTime;
    private String detail;
    private String[] contactWay;
    private String imageUrl;
}
