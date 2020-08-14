package com.qrcodemall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@SpringBootApplication
@MapperScan("com.qrcodemall.dao")
//@ComponentScan("com.qrcodemall.controller")
//目前版本0.0.10
public class QrcodemallApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrcodemallApplication.class, args);
    }

}
