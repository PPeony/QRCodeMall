package com.qrcodemall.controller;

import com.qrcodemall.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Peony
 * @Date: 2020/7/23 15:54
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/register")
    public Result register(String account, String password) {
        Result result = new Result();
        //
        if (account == null || password == null) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("输入字段不能为空");
            return result;
        }
        if (account.equals("123@qq.com")) {//email
            if (password.equals("123456")) {
                result.setCode(HttpStatus.OK.value());
                result.setMessage("登录成功");
                return result;
            } else {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("用户名或者密码不正确");
                return result;
            }
        } else if (account.equals("18845678900")) {//phone
            if (password.equals("123")) {
                result.setCode(HttpStatus.OK.value());
                result.setMessage("登录成功");
                return result;
            } else {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("用户名或者密码不正确");
                return result;
            }
        } else if (account.equals("zhangsan")) {//name
            if (password.equals("123456789")) {
                result.setCode(HttpStatus.OK.value());
                result.setMessage("登录成功");
                return result;
            } else {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("用户名或者密码不正确");
                return result;
            }
        }
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setMessage("该用户未注册");
        return result;
    }
}
