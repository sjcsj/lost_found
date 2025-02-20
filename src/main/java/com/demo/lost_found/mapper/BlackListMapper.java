package com.demo.lost_found.mapper;

import com.demo.lost_found.pojo.BlackList;
import com.demo.lost_found.pojo.form.BlackListAdminForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlackListMapper {

    List<BlackList> getBlackList(@Param("userIds") List<Integer> userIds,
                                      @Param("adminIds") List<Integer> adminIds,
                                      @Param("status") Integer status);

    BlackList getUserStatus(@Param("userId") Integer userId);

    void updateById(BlackList blackList);

    void insertInfo(BlackList blackList);

    void releaseUserById(@Param("id") Integer id, @Param("now") String now);

    List<BlackList> selectHistoryByUserId(@Param("id") Integer id);
}
