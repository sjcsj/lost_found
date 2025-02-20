package com.demo.lost_found.controller.admin;

import com.demo.lost_found.pojo.BlackList;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.form.BlackInfoForm;
import com.demo.lost_found.pojo.form.BlackListAdminForm;
import com.demo.lost_found.pojo.form.UserAdminForm;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 黑名单管理
 * 后台相关接口必须以/admin开头
 */
@RestController
@RequestMapping("/admin/blackList")
public class BlackListAdminController {

    @Autowired
    private BlackListService blackListService;

    /**
     * 查询黑名单列表
     * @param blackListAdminForm
     * @return
     */
    @PostMapping("/getBlackList")
    public BaseResponse<List<BlackList>> getBlackList(@RequestBody BlackListAdminForm blackListAdminForm) {
        List<BlackList> blackList = blackListService.getBlackList(blackListAdminForm);
        return new BaseResponse<>(200,"查询成功", blackList);
    }

    /**
     * 根据userId查询该用户当前是否处于拉黑状态，如果处于拉黑中，返回拉黑记录
     * @return
     */
    @GetMapping("/getUserStatus")
    public BaseResponse<BlackList> getUserStatus(@RequestParam("id") Integer userId) {
        return blackListService.getUserStatus(userId);
    }

    /**
     * 添加拉黑信息
     */
    @PostMapping("/addBlackInfo")
    public BaseResponse<String> addBlackInfo(@RequestBody BlackInfoForm blackInfoForm) {
        return blackListService.addBlackInfo(blackInfoForm);
    }

    /**
     * 导出黑名单
     */
    @PostMapping("/export")
    public void export(@RequestBody BlackListAdminForm blackListAdminForm, HttpServletResponse httpServletResponse) throws IOException {
        List<BlackList> blackList = blackListService.getBlackList(blackListAdminForm);
        blackListService.export(blackList, httpServletResponse);
    }

    /**
     * 立即释放被拉黑的用户
     */
    @GetMapping("/releaseUserById")
    public BaseResponse<String> releaseUserById(@RequestParam("userId") Integer id) {
        return blackListService.releaseUserById(id);
    }

    /**
     * 修改拉黑记录
     */
    @PostMapping("/updateBlackInfo")
    public BaseResponse<String> updateBlackInfo(@RequestBody BlackList blackList) {
        return blackListService.updateBlackInfo(blackList);
    }

    /**
     * 根据用户id获取该用户的拉黑历史
     */
    @GetMapping("/getBlackHistory")
    public BaseResponse<List<BlackList>> getBlackHistory(@RequestParam("userId") Integer id) {
        return blackListService.getBlackHistory(id);
    }

}
