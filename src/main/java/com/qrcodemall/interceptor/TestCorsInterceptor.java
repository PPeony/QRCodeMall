package com.qrcodemall.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Peony
 * @Date: 2022/3/18 14:35
 */
@Component
public class TestCorsInterceptor implements HandlerInterceptor {
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2)
            throws Exception {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//支持跨域请求
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token
        return true;
    }
}
