package com.demo.lost_found.service.impl;

import com.demo.lost_found.mapper.LostAndFoundMapper;
import com.demo.lost_found.mapper.LostAndReportMapper;
import com.demo.lost_found.mapper.UserAdminMapper;
import com.demo.lost_found.mapper.UserMapper;
import com.demo.lost_found.pojo.vo.DataStatisticsVO;
import com.demo.lost_found.rep.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.lost_found.service.DataStatisticsAdminService;

@Service
public class DataStatisticsAdminServiceImpl implements DataStatisticsAdminService {

    @Autowired
    private UserAdminMapper userAdminMapper;

    @Autowired
    private LostAndFoundMapper lostAndFoundMapper;

    @Autowired
    private LostAndReportMapper lostAndReportMapper;

    @Override
    public BaseResponse<DataStatisticsVO> getData() {
        DataStatisticsVO res = new DataStatisticsVO();
        res.setTotalUsers(userAdminMapper.getCount());
        res.setBoundPhoneUsers(userAdminMapper.getCount1());
        res.setClaimedItems(lostAndFoundMapper.getCount());
        res.setUnclaimedItems(lostAndFoundMapper.getCount1());
        res.setFoundItems(lostAndReportMapper.getCount());
        res.setUnfoundItems(lostAndReportMapper.getCount1());

        res.setLostFoundApproved(lostAndFoundMapper.getCount2());
        res.setLostFoundRejected(lostAndFoundMapper.getCount3());
        res.setLostFoundPending(lostAndFoundMapper.getCount4());
        res.setLostReportApproved(lostAndReportMapper.getCount2());
        res.setLostReportRejected(lostAndReportMapper.getCount3());
        res.setLostReportPending(lostAndReportMapper.getCount4());
        return new BaseResponse<>(200, "获取成功", res);
    }
}
