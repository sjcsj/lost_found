package com.demo.lost_found.pojo.form;

import lombok.Data;

@Data
public class ReviewForm {

    private Integer id;
    private String checkStatus;
    private String failureReason;
}
