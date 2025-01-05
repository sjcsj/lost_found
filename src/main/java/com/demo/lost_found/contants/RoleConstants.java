package com.demo.lost_found.contants;

import lombok.Data;

import java.util.HashMap;

/**
 * 角色中英文映射
 */
@Data
public class RoleConstants {

    public static HashMap<String, String> map = new HashMap<>();

    public static final String USER = "user";

    public static final String ADMIN = "admin";

    static {
        map.put(USER, "普通用户");
        map.put(ADMIN, "管理员");
    }

}
