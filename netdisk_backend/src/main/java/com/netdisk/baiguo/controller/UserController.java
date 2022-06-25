package com.netdisk.baiguo.controller;

import com.netdisk.baiguo.domain.User;
import com.netdisk.baiguo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/newUser")
    public User newUser(@RequestBody User user) {
        try {
            if (userService.addUser(user)) {
                String millis = String.valueOf(System.currentTimeMillis());
                stringRedisTemplate.opsForValue().set(user.getId() + "", millis);
                user.setPassword(millis);
                return user;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @RequestMapping("/login")
    public User login(@RequestBody User user) {
        User dbUser = userService.getByEmail(user.getEMail());
        if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
            String millis = String.valueOf(System.currentTimeMillis());
            stringRedisTemplate.opsForValue().set(user.getId() + "", millis);
            dbUser.setPassword(millis);
            System.out.println("成功");
            return dbUser;
        }
        return null;
    }

    @RequestMapping("/getUser")
    public User getUser(@CookieValue("userId") int userId) {
        return userService.getUser(userId);
    }
}
