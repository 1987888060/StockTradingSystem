package com.zxy.config.interceptor;

import com.zxy.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component(value = "authenticationInterceptor")
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }
        return true;

    }
}
