package com.demo.lost_found.utils;

import java.util.Random;

/**
 * 用于生成验证码
 */
public class VerificationCodeUtils {

    /**
     * 生成六位数字验证码
     *
     * @return 六位数字验证码
     */
    public static String generateNumericCode() {
        Random random = new Random();
        // 使用StringBuilder拼接四位随机数字
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            // 生成0-9之间的随机整数
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }

    public static void main(String[] args) {
        // 测试验证码生成
        for (int i = 0; i < 5; i++) {
            System.out.println("验证码: " + generateNumericCode());
        }
    }
}
