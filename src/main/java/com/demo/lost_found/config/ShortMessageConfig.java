package com.demo.lost_found.config;

import com.aliyun.dysmsapi20170525.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * aliyun短信服务配置
 */
@Configuration
public class ShortMessageConfig {

    @Value("${aliyun.accesskey-id}")
    private String accessKeyId;

    @Value("${aliyun.accesskey-secret}")
    private String accessKeySecret;

    @Bean(name = "shortMessageClient")
    public Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
}
