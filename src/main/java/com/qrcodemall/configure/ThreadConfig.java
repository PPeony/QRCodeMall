package com.qrcodemall.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Peony
 * @Date: 2020/8/30 9:00
 */
//线程池，没用上
@Configuration
public class ThreadConfig {
    @Bean
    public ExecutorService getExecutorTools(){
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        return  executorService;
    }
}
