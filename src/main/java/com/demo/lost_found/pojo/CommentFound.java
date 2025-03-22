package com.demo.lost_found.pojo;

import lombok.Data;

@Data
public class CommentFound {

    private Integer id;
    private Integer lostAndFoundId;
    private Integer userId;
    private String comment;

    // 以下信息根据userId从user表中查询
    private String username;
    private String avatar;

}
