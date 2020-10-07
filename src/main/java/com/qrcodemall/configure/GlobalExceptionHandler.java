package com.qrcodemall.configure;

import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Peony
 * @Date: 2020/7/27 10:43
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest req) {
        //打印错误
        log.error("error: ",e);
        e.printStackTrace();
        Result result = new Result();
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            //404
            result.setCode(HttpStatus.NOT_FOUND.value());
            result.setMessage("找不到页面");
        } else if (e instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            //405
            result.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
            result.setMessage("请求类型不正确");
        } else if (e instanceof GlobalException) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(e.getMessage());
        } else {
            //500
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage("服务器异常");
        }


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
