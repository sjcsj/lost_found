package com.demo.lost_found.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class Authority {

    @ExcelProperty("主键")
    @ColumnWidth(20)
    private Integer id;

    @ExcelProperty("路径")
    @ColumnWidth(40)
    private String url;

    @ExcelProperty("接口描述")
    @ColumnWidth(40)
    private String description;

    @ExcelProperty("是否启用")
    @ColumnWidth(20)
    private String enable;

    @ExcelIgnore
    private Boolean enable1;

    @ExcelIgnore
    private String deletedAt;
}
