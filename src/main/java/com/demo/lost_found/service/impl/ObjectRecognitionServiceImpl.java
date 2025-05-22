package com.demo.lost_found.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.demo.lost_found.pojo.vo.RecognitionVO;
import com.demo.lost_found.service.ObjectRecognitionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ObjectRecognitionServiceImpl implements ObjectRecognitionService {

    public static final String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";

    public static final String RECOGNIZE_URL = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";

    @Value("${baidu.api-key}")
    public String apiKey;

    @Value("${baidu.secret-key}")
    public String secretKey;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String TOKEN_REDIS_KEY = "baidu:token:key";

    @Override
    public RecognitionVO getResult(String url) {
        String token = getToken();
        String recUrl = RECOGNIZE_URL + "?" + "url=" + url + "&baike_num=" + 1 + "&access_token=" + token;
        // 发送 POST 请求
        HttpResponse response = HttpRequest.post(recUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .execute();
        String body = response.body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        log.info(body);
        // 获取result数组
        JSONObject firstResult = JSONUtil.parseArray(jsonObject.get("result")).getJSONObject(0);
        String root = firstResult.getStr("root");
        String keyword = firstResult.getStr("keyword");
        JSONObject baikeInfo = firstResult.getJSONObject("baike_info");
        String baikeUrl = baikeInfo.getStr("baike_url");
        String description = baikeInfo.getStr("description");
        RecognitionVO recognitionVO = new RecognitionVO(root, keyword, baikeUrl, description);
        return recognitionVO;
    }

    // 获取token
    @Override
    public String getToken() {
        // 先去redis中查询
        String token = stringRedisTemplate.opsForValue().get(TOKEN_REDIS_KEY);
        if (StringUtils.isNotEmpty(token)) {
            return token;
        }
        // 没有再去调用获取token的接口
        // 请求地址（带查询参数）
        String url = TOKEN_URL + "?" + "client_id=" + apiKey + "&client_secret=" + secretKey +
                "&grant_type=client_credentials";
        // 发送 POST 请求
        HttpResponse response = HttpRequest.of(url)
                .method(Method.POST)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .execute();

        String body = response.body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        String accessToken = jsonObject.getStr("access_token");
        stringRedisTemplate.opsForValue().set(TOKEN_REDIS_KEY, accessToken, 20, TimeUnit.DAYS);
        return accessToken;
    }
}
