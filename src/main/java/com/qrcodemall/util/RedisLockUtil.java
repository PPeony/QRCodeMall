package com.qrcodemall.util;

import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @Author: Peony
 * @Date: 2022/3/29 13:23
 */
@Component
public class RedisLockUtil {
//    redis实现分布式锁
    @Autowired
    JedisPool jedisPool;

    public boolean tryLockWithSet(String key, String UniqueId, int seconds) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return "OK".equals(jedis.set(key, UniqueId, "NX", "EX", seconds));
    }

    public boolean releaseLockWithLua(String key,String value) {
//        使用lua脚本保证原子性,解锁时，我们需要判断锁是否是自己的,再解锁
        @Cleanup Jedis jedis = jedisPool.getResource();
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then " +
                "return redis.call('del',KEYS[1]) else return 0 end";
        return jedis.eval(luaScript, Collections.singletonList(key), Collections.singletonList(value)).equals(1L);
    }

}
