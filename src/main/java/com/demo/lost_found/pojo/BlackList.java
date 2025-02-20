package com.demo.lost_found.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class BlackList {

    @ExcelIgnore
    private Integer id;

    @ExcelIgnore
    private Integer userId;

    @ExcelIgnore
    private Integer adminId;

    @ExcelProperty("被拉黑的用户名")
    @ColumnWidth(20)
    private String username;

    @ExcelProperty("执行操作的管理员用户名")
    @ColumnWidth(20)
    private String adminname;

    @ExcelProperty("释放时间")
    @ColumnWidth(20)
    private String deadline;

    @ExcelIgnore
    private Integer status;

    // 拉黑状态的中文
    @ExcelProperty("拉黑状态")
    @ColumnWidth(20)
    private String statusName;

    @ExcelProperty("拉黑理由")
    @ColumnWidth(20)
    private String reason;

    @ExcelProperty("备注")
    @ColumnWidth(20)
    private String notes;

    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private String createdAt;

    @ExcelProperty("更新时间")
    @ColumnWidth(20)
    private String updatedAt;
}
