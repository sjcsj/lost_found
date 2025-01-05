package com.demo.lost_found;

import cn.hutool.core.date.DateUtil;
import com.demo.lost_found.mapper.UserMapper;
import com.demo.lost_found.pojo.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static com.demo.lost_found.utils.MD5Util.md5Lower;

@SpringBootTest
public class CreateData {

    @Autowired
    private UserMapper userMapper;

    @Value("${minio.server-url}")
    private String minioUrl;

    Faker faker = new Faker(); // 初始化 Faker
    Random random = new Random();

    @Test
    public void createUserData() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            String fakeUsername = faker.name().username(); // 假用户名
            String fakePassword = md5Lower("1234");
            String fakeRole = "user";
            String fakeGender = random.nextBoolean() ? "男" : "女"; // 假性别
            String fakeAvatar = minioUrl + "/lost-found/default.jpg"; // 假头像 URL
            String fakeCreatedAt = DateUtil.now();
            user.setUsername(fakeUsername);
            user.setPassword(fakePassword);
            user.setRole(fakeRole);
            user.setGender(fakeGender);
            user.setAvatar(fakeAvatar);
            user.setCreatedAt(fakeCreatedAt);
            userMapper.addUser(user);
        }
    }
}
