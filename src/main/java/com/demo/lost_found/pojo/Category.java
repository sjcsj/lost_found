package com.demo.lost_found.pojo;

import lombok.Data;

@Data
public class Category {

    private Integer id;
    private String categoryName;
    private Integer count;
    private Integer sort;
}
