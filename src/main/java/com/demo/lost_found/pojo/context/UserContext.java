package com.demo.lost_found.pojo.context;

import com.demo.lost_found.pojo.User;

/**
 * 使用ThreadLocal存放当前用户信息
 */
public class UserContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}