package com.demo.lost_found.contants;

import lombok.Data;

import java.util.HashMap;

/**
 * 拉黑状态
 */
@Data
public class BlackStatusConstants {

    public static final HashMap<Integer, String> map = new HashMap<>();

    static {
        map.put(1, "拉黑中");
        map.put(2, "已释放");
    }
}
