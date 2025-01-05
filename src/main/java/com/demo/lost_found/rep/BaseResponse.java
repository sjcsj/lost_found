package com.demo.lost_found.rep;

import lombok.Data;
import lombok.Getter;

@Data
public class BaseResponse<T> {

    // 状态码
    private Integer code;

    // 提示信息
    private String message;

    // 返回数据
    private T data;

    public BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
