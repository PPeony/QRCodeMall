package com.qrcodemall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@SpringBootApplication
//@ComponentScan("com.qrcodemall.controller")
public class QrcodemallApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrcodemallApplication.class, args);
    }

}
