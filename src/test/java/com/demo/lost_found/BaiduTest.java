package com.demo.lost_found;

import com.demo.lost_found.service.ObjectRecognitionService;
import com.demo.lost_found.utils.BaiduUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaiduTest {

    @Autowired
    public ObjectRecognitionService objectRecognitionService;

    @Autowired
    public BaiduUtil baiduUtil;

    @Test
    public void test1() {
        String token = objectRecognitionService.getToken();
        System.out.println(token);
    }

    @Test
    public void test2() {
        boolean b = baiduUtil.textDetection("去你妈的吧");
        System.out.println(b);
    }
}
