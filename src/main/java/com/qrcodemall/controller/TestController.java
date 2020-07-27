package com.qrcodemall.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Peony
 * @Date: 2020/7/27 16:43
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/post")
    public String post(@RequestParam("name") String name,@RequestParam("password") String password) {
        System.out.println(name+" *** "+password);
        return "success";
    }
}
