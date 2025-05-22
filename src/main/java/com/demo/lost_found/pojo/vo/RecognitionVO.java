package com.demo.lost_found.pojo.vo;

import lombok.Data;

@Data
public class RecognitionVO {

    // 分类
    private String root;

    // 物品名称
    private String keyword;

    // 百度百科地址
    private String baikeUrl;

    // 物品描述
    private String description;

    public RecognitionVO(String root, String keyword, String baikeUrl, String description) {
        this.root = root;
        this.keyword = keyword;
        this.baikeUrl = baikeUrl;
        this.description = description;
    }
}
