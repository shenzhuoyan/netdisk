package com.netdisk.baiguo.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public LoginHandlerInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        Cookie[] cookies =  request.getCookies();
        int userId = 0;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userId")){
                    userId = Integer.parseInt(cookie.getValue());
                    String token =request.getHeader("token");
                    String dbToken = stringRedisTemplate.opsForValue().get(userId);
                    if(token.equals(dbToken)) {
                        return true;
                    }
                }
            }
        }
        //指定相应的字符集：解决响应的乱码问题。这里的代码是直接作为网页显示到前端
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }
}
