package edu.dongguk.cs25backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class UserIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("USER_ID", SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("userId = {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
