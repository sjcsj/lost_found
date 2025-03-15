package com.demo.lost_found.interceptor;

import cn.hutool.json.JSONUtil;
import com.demo.lost_found.contants.RedisConstants;
import com.demo.lost_found.contants.RoleConstants;
import com.demo.lost_found.contants.WhiteList;
import com.demo.lost_found.pojo.Authority;
import com.demo.lost_found.pojo.User;
import com.demo.lost_found.pojo.context.UserContext;
import com.demo.lost_found.rep.BaseResponse;
import com.demo.lost_found.service.AuthorityAdminService;
import com.demo.lost_found.service.UserAdminService;
import com.demo.lost_found.utils.Auth0JwtUtils;
import com.github.javafaker.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 拦截请求，判断当前用户是否绑定手机号，如果没有绑定则无法访问一些接口
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthorityAdminService authorityAdminService;

    @Value("${auth.enable}")
    private Boolean auth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 是否开启鉴权
        if (!auth) {
            log.info("鉴权被关闭，所有请求直接放行");
            return true;
        }
        log.info("----------------------开始鉴权----------------------");
        // 如果是白名单的路径直接放行
        String requestURI = request.getRequestURI();
        log.info("当前url为{}", requestURI);
        if (WhiteList.WHITE_LIST.contains(requestURI)) {
            log.info("白名单路径，直接放行");
            return true;
        }
        // 获取token,提取token
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            // 没有token，返回401
            log.info("请求头没有携带token,返回401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String token = "";
        if (authorization.startsWith("Bearer ")) {
            // 从第8个字符开始截取
            token = authorization.substring(7);
        }
        // 获取用户id
        String id = stringRedisTemplate.opsForValue().get(RedisConstants.TOKEN_PREFIX + token);
        if (id == null) {
            // token无效或失效
            log.info("token无效或已经失效,返回401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        // token有效，查询用户信息，根据用户角色来鉴权
        // 注意普通用户无法访问管理员相关接口，管理员也无法访问普通用户相关接口
        User user = userAdminService.getUserInfoById(Integer.parseInt(id)).getData();
        log.info("当前用户角色为{}", user.getRole());
        if (RoleConstants.USER.equals(user.getRole())) {
            // 角色为普通用户
            // 普通用户无法访问后台的所有接口，一旦访问了就会弹出提示信息显示没有权限并跳转到登录页
            if (antPathMatcher.match("/admin/**", requestURI)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setHeader("error-message", URLEncoder.encode("普通用户无法访问后台管理系统，请登录管理员账号", "utf8"));
                return false;
            }
            // 没有绑定手机号的用户无法访问前台的一部分接口
            // 通过查表来得到这些接口
            List<Authority> authorityList = authorityAdminService.getEnable();
            if (user.getPhone() == null) {
                for (Authority item : authorityList) {
                    if (requestURI.equals(item.getUrl())) {
                        log.info("当前用户没有绑定手机号，无法访问");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setHeader("error-message", URLEncoder.encode("绑定手机号后才能使用该功能", "utf8"));
                        return false;
                    }
                }
            }
        } else if (RoleConstants.ADMIN.equals(user.getRole())) {
            // 角色为管理员
            // 管理员无法访问前台的所有接口，一旦访问了就会弹出提示信息显示没有权限并跳转到登录页
            if (!antPathMatcher.match("/admin/**", requestURI)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setHeader("error-message", URLEncoder.encode("管理员无法访问前台，请登录普通用户账号", "utf8"));
                return false;
            }
        }
        // 刷新用户token过期时间
        stringRedisTemplate.opsForValue().set(RedisConstants.TOKEN_PREFIX + token, id, 30, TimeUnit.MINUTES);
        // 将用户信息存入ThreadLocal，后续接口则无需再去查询用户信息
        UserContext.setCurrentUser(user);
        return true;
    }

    /**
     * 防止内存泄漏
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear(); // 清除 ThreadLocal 数据
    }
}














