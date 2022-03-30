package com.qrcodemall.aop;

import com.qrcodemall.aop.annotation.RedisLock;
import com.qrcodemall.util.JedisUtil;
import com.qrcodemall.util.RedisLockUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Author: Peony
 * @Date: 2022/3/29 13:52
 */
@Aspect
@Component
@Slf4j
public class LockMethodAspect {
    @Autowired
    private RedisLockUtil redisLockHelper;
    @Autowired
    JedisUtil jedisUtil;

    @Around("@annotation(com.qrcodemall.aop.annotation.RedisLock)")
    public Object around(ProceedingJoinPoint joinPoint) {
        Jedis jedis = jedisUtil.getJedis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String value = UUID.randomUUID().toString();
        String key = redisLock.key();
        try {
            final boolean islock = redisLockHelper.tryLockWithSet(key,value,60);
            log.info("isLock : {}",islock);
            if (!islock) {
                log.error("获取锁失败");
                throw new RuntimeException("获取锁失败");
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException("系统异常");
            }
        }  finally {
            log.info("释放锁");
            redisLockHelper.releaseLockWithLua(key,value);
            jedis.close();
        }
    }
}


