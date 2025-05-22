package com.demo.lost_found.controller.user;

import com.demo.lost_found.pojo.vo.RecognitionVO;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.ObjectRecognitionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/object_recognition")
public class ObjectRecognitionController {

    @Autowired
    private ObjectRecognitionService objectRecognitionService;

    /**
     * 智能识别
     * @param url
     * @return
     */
    @GetMapping("/getResult")
    public BaseResponse<RecognitionVO> getResult(@RequestParam("url") String url) {
        RecognitionVO res = objectRecognitionService.getResult(url);
        return new BaseResponse<>(200, "识别成功", res);
    }
}
