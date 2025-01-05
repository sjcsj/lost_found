package com.demo.lost_found.contants;

import lombok.Data;

@Data
public class RedisConstants {

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "user:token:";

    /**
     * 存放用户信息
     */
    public static final String USER_INFO = "user:info:";

    /**
     * 存放用户手机号和对应的验证码(登陆用)
     */
    public static final String USER_PHONE_CODE_LOGIN = "user:phone:code:login:";

    /**
     * 存放用户手机号和对应的验证码(绑定用)
     */
    public static final String USER_PHONE_CODE_BIND = "user:phone:code:bind:";

}
