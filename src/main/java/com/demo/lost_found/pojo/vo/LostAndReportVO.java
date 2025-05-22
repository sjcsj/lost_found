package com.demo.lost_found.pojo.vo;

import com.demo.lost_found.pojo.LostAndFound;
import com.demo.lost_found.pojo.LostAndReport;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LostAndReportVO {
    // 通过
    public List<LostAndReport> approved;
    // 未通过
    public List<LostAndReport> rejected;
    // 待审核
    public List<LostAndReport> pending;

    public LostAndReportVO() {
        approved = new ArrayList<>();
        rejected = new ArrayList<>();
        pending = new ArrayList<>();
    }
}
