package com.demo.lost_found.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.demo.lost_found.pojo.vo.RecognitionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BaiduUtil {

    public static final String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";

    public static final String TEXT_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined";

    @Value("${baidu.api-key}")
    public String apiKey;

    @Value("${baidu.secret-key}")
    public String secretKey;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String TOKEN_REDIS_KEY = "baidu:token:key";

    /**
     * 合规返回true，其他为false
     * @param text
     * @return
     */
    public boolean textDetection(String text) {
        String token = getToken();
        String recUrl = TEXT_URL + "?" + "text=" + text + "&access_token=" + token;
        // 发送 POST 请求
        HttpResponse response = HttpRequest.post(recUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .execute();
        String body = response.body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        log.info(body);
        String conclusion = jsonObject.getStr("conclusion");
        if ("合规".equals(conclusion)) {
            return true;
        } else {
            return false;
        }
    }

    // 获取token
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
