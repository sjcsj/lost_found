package com.demo.lost_found.pojo.vo;

import com.demo.lost_found.pojo.LostAndFound;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LostAndFoundVO {
    // 通过
    public List<LostAndFound> approved;
    // 未通过
    public List<LostAndFound> rejected;
    // 待审核
    public List<LostAndFound> pending;

    public LostAndFoundVO() {
        approved = new ArrayList<>();
        rejected = new ArrayList<>();
        pending = new ArrayList<>();
    }
}
