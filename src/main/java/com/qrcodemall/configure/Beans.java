package com.qrcodemall.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

/**
 * @Author: Peony
 * @Date: 2020/8/30 9:00
 */
//线程池，没用上
@Configuration
public class Beans {
//    @Bean
//    public JedisPoolConfig jedisPoolConfig(@Value("${jedis.maxTotal}") int maxActive,
//                                           @Value("${jedis.maxIdle}") int maxIdle,
//                                           @Value("${jedis.minIdle}") int minIdle,
//                                           @Value("${jedis.maxWaitMillis}") long maxWaitMillis,
//                                           @Value("${jedis.testOnBorrow}") boolean testOnBorrow) {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
//
//        return jedisPoolConfig;
//    }
//
//    @Bean
//    public JedisPool jedisPool(@Value("${jedis.host}") String host,
//                               @Value("${jedis.password}") String password,
//                               @Value("${jedis.port}") int port,
//                               @Value("${jedis.timeout}") int timeout, JedisPoolConfig jedisPoolConfig) {
//
//        if(password != null && password.length() > 0) {
//            return new JedisPool(jedisPoolConfig, host, port, timeout, password);
//        }
//
//        return new JedisPool(jedisPoolConfig, host, port, timeout);
//    }
    @Bean
    public ExecutorService getExecutorTools(){
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        return  executorService;
    }
    @Bean(name = "orderFormMap")
    public Map<String,Object> getMap() {
        Map<String,Object> map = new ConcurrentHashMap<>();
        return map;
    }
    @Bean(name = "concurrentMap")
    public Map<String, ScheduledFuture> getConcurrentMap() {
        return new ConcurrentHashMap<String,ScheduledFuture>();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
