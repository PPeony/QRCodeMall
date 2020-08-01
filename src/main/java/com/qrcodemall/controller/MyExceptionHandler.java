package com.qrcodemall.controller;

import com.qrcodemall.util.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Peony
 * @Date: 2020/7/31 11:32
 */
@RestController
@Slf4j
@Api(value = "处理服务器异常，返回code500")
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest req) {
        //打印错误
        e.printStackTrace();
        Result result = new Result();
        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMessage("服务器异常");
        return result;
        //检查请求是否为ajax, 如果是 ajax 请求则返回 Result json串, 如果不是 ajax 请求则返回 error 视图
//        String contentTypeHeader = req.getHeader("Content-Type");
//        String acceptHeader = req.getHeader("Accept");
//        String xRequestedWith = req.getHeader("X-Requested-With");
//        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
//                || (acceptHeader != null && acceptHeader.contains("application/json"))
//                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
//            return result;
//        } else {
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.addObject("message", e.getMessage());
//            modelAndView.addObject("url", req.getRequestURL());
//            modelAndView.addObject("stackTrace", e.getStackTrace());
//            modelAndView.addObject("author", "十三");
//            modelAndView.addObject("ltd", "新峰商城");
//            modelAndView.setViewName("error/error");
//            return modelAndView;
//        }
    }
}
