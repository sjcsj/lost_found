package com.demo.lost_found.pojo.form;

import com.demo.lost_found.rep.BaseResponse;
import lombok.Data;

@Data
public class BlackInfoForm {

    private Integer userId;

    private String deadline;

    private String reason;

    private String notes;

}
