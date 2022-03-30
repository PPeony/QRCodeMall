package com.qrcodemall.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Peony
 * @Date: 2022/3/30 13:53
 */
@Component
@Slf4j
public class JedisUtil {

    @Value("${jedis.host}") private String host;
    @Value("${jedis.password}") private String password;
    @Value("${jedis.maxTotal}") private int maxActive;
    @Value("${jedis.maxIdle}") private int maxIdle;
    @Value("${jedis.minIdle}") private int minIdle;
    @Value("${jedis.maxWaitMillis}") private long maxWaitMillis;
    @Value("${jedis.testOnBorrow}") private boolean testOnBorrow;
    @Value("${jedis.port}") int port;
    @Value("${jedis.timeout}") int timeout;
    @Value("${jedis.maxTotal}")int maxTotal;
    private  Map<String, JedisPool> map = new ConcurrentHashMap<>();

    private  JedisPool getPool() {


        String key = this.host + ":" + this.host;
        JedisPool pool;
        if (!map.containsKey(key)) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(this.maxIdle);
            config.setMaxWaitMillis(this.maxWaitMillis);
            config.setMinIdle(this.minIdle);
            config.setMaxTotal(this.maxTotal);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            System.out.println("jedisPool config=> "+this.maxIdle+" "+this.maxTotal+" "+this.minIdle);
            pool = new JedisPool(config,this.host,this.port,this.timeout);
            map.put(key, pool);
        } else {
            pool = map.get(key);
            System.out.println(System.currentTimeMillis()+" getPool active=>"+
                    pool.getNumActive()+" waiter=> "+pool.getNumWaiters()+" idle=> "+
                    pool.getNumIdle());
        }
        return pool;
    }

    public Jedis getJedis() {
        Jedis jedis = null;
        int count = 0;
        do {
            try {
                jedis = getPool().getResource();
                count++;
            } catch (Exception e) {
                log.error("get jedis failed ", e);
                if (jedis != null) {
                    jedis.close();
                }
            }
        } while (jedis == null && count < 5);
//        重试次数
        return jedis;
    }
}
