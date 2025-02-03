package com.memo.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String uri = request.getRequestURI();
        log.info("[#### preHandle] uri:{}", uri);

        // 로그인 여부 - 세션
        HttpSession session = request.getSession();
        Integer userId = (Integer)session.getAttribute("userId");

        if(uri.startsWith("/post") && userId == null){
            response.sendRedirect("/user/sign-in-view");
            return false; // 원래 가려했던 컨트롤러 수행 방지
        }

        if(uri.startsWith("/user") && userId != null){
            response.sendRedirect("/post/post-list-view");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler
    , ModelAndView mav) {

        // view와 model이 있음. (html 해석전)
        log.info("[#### postHandle]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

        log.info("[#### afterCompletion]");
    }
}
