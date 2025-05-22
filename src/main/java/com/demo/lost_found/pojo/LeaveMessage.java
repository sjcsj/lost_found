package com.demo.lost_found.pojo;

import lombok.Data;

@Data
public class LeaveMessage {

    private Integer id;
    private Integer userId;
    private String comment;

    private String username;
    private String phone;
}
