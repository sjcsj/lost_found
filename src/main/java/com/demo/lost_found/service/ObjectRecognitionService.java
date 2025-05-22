package com.demo.lost_found.service;

import com.demo.lost_found.pojo.vo.RecognitionVO;

public interface ObjectRecognitionService {
    RecognitionVO getResult(String url);

    String getToken();
}
