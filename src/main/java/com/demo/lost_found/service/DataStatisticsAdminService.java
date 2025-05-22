package com.demo.lost_found.service;

import com.demo.lost_found.pojo.vo.DataStatisticsVO;
import com.demo.lost_found.rep.BaseResponse;

public interface DataStatisticsAdminService {
    BaseResponse<DataStatisticsVO> getData();
}
