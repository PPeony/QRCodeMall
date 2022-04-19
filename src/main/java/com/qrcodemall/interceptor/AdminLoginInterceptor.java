package com.qrcodemall.interceptor;

import com.qrcodemall.util.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Peony
 * @Date: 2022/3/31 17:49
 */
//废弃
public class AdminLoginInterceptor implements HandlerInterceptor {
    // 目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("admin");
        // 如果获取的request的session中的loginUser参数为空（未登录），就返回登录页，否则放行访问
        if (user == null) {
            // 未登录，给出错误信息，
            // 获取request返回页面到登录页
            setReturn(response,HttpStatus.UNAUTHORIZED.value(),"未登录");
            return false;
        } else {
            // 已登录，放行
            //setReturn(response,HttpStatus.OK.value(),"已登录");
            return true;
        }
    }
    private static void setReturn(HttpServletResponse response, int status, String msg){
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        //UTF-8编码
        httpResponse.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result result = new Result();
        result.code(status).message(msg);
        String json = JSON.toJSONString(result);
        try {
            httpResponse.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return;
        }
    }

}
