package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.LeaveMessage;
import com.demo.lost_found.pojo.vo.DataStatisticsVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.DataStatisticsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/dataStatistics")
public class DataStatisticsAdminController {

    @Autowired
    private DataStatisticsAdminService dataStatisticsAdminService;

    /**
     * 获取各种统计数据
     */
    @GetMapping("/getData")
    public BaseResponse<DataStatisticsVO> getData() {
        return dataStatisticsAdminService.getData();
    }
}
