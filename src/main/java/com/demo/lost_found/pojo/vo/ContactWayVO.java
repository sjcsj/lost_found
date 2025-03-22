package com.demo.lost_found.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 当前用户联系方式
 */
@Data
@AllArgsConstructor
public class ContactWayVO {

    // 0为手机号，1为微信号
    private Integer id;
    // 手机号/微信号
    private String contactWayName;
}
