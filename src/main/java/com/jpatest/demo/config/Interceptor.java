package com.jpatest.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jpatest.demo.model.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class Interceptor implements HandlerInterceptor{
    private Logger logger = LoggerFactory.getLogger(Interceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("Interceptor preHandle");
        HttpSession session = request.getSession();
        UserVO loginVO = (UserVO) session.getAttribute("loginUser");
        // 예외 URI 적용
        if(request.getRequestURI().indexOf("/userLogin.do") >= 0
            || request.getRequestURI().indexOf("/loginUser.do") >= 0
            || request.getRequestURI().indexOf("/userRegist.do") >= 0
            || request.getRequestURI().indexOf("/getUserRegist.do") >= 0){
                session.setMaxInactiveInterval(30*60);
                return true;
            }
        if(ObjectUtils.isEmpty(loginVO)){
            response.sendRedirect("/userLogin.do");
            return false;
        }else{
            session.setMaxInactiveInterval(30*60);
            return true;
        }
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
                logger.info("Interceptor postHandle");
        
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("Interceptor afterHandle");
    }

    
}
