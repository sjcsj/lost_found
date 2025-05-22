package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.LeaveMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveMessageMapper {

    List<LeaveMessage> getMessage();

    void insert(LeaveMessage leaveMessage);

    void delete(Integer id);

    List<LeaveMessage> getMessageOfCurrentUser(Integer id);
}
