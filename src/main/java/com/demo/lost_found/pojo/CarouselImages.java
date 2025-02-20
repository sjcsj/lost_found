package com.demo.lost_found.pojo;

import com.github.javafaker.Bool;
import lombok.Data;

@Data
public class CarouselImages {

    private Integer id;

    private String imageUrl;

    private String createdAt;

    private String jumpLink;

    private Integer sort;

    private Integer clicks;

    private String enable;

    private Boolean enable1;
}
