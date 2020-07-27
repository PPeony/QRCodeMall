package com.qrcodemall.configure;

import com.qrcodemall.common.UserException;
import com.qrcodemall.util.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Peony
 * @Date: 2020/7/27 10:43
 */
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public Result userExceptionHandler(UserException e) {
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return result;
    }
}
