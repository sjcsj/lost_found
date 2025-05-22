package com.demo.lost_found.pojo.vo;

import lombok.Data;

@Data
public class DataStatisticsVO {

    // 总用户数
    private Integer totalUsers;
    // 已绑定手机号用户数
    private Integer boundPhoneUsers;
    // 失物招领信息已认领数
    private Integer claimedItems;
    // 失物招领信息未认领数
    private Integer unclaimedItems;
    // 物品挂失信息已找回数
    private Integer foundItems;
    // 物品挂失信息未找回数
    private Integer unfoundItems;

    private Integer lostFoundApproved;
    private Integer lostFoundRejected;
    private Integer lostFoundPending;
    private Integer lostReportApproved;
    private Integer lostReportRejected;
    private Integer lostReportPending;
}
