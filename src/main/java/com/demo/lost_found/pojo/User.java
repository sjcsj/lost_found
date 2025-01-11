package com.demo.lost_found.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class User {
    @ExcelIgnore
    private Integer id;

    @ExcelProperty("用户名")
    @ColumnWidth(20)
    private String username;

    @ExcelIgnore
    private String password;

    @ExcelProperty("手机号")
    @ColumnWidth(20)
    private String phone;

    @ExcelProperty("微信号")
    @ColumnWidth(20)
    private String wechatId;

    @ExcelProperty("角色")
    @ColumnWidth(20)
    private String role;

    @ExcelProperty("性别")
    @ColumnWidth(20)
    private String gender;

    @ExcelIgnore
    private String avatar;

    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private String createdAt;

    @ExcelIgnore
    private String updatedAt;

    @ExcelIgnore
    private String deletedAt;
}
