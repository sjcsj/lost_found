package com.demo.lost_found.pojo;

import lombok.Data;

@Data
public class Announcement {

    private Integer id;
    private String title;
    private String content;
    private String imageUrl;
    private Integer createdBy;
    private String updatedAt;

    private String username;
}
