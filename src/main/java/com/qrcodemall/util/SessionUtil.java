package com.qrcodemall.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qrcodemall.common.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @Author: Peony
 * @Date: 2022/4/24 14:51
 */
@Component
public class SessionUtil {
    @Autowired
    JedisUtil jedisUtil;
    //集群部署时，session信息存在redis里面，sessionId作为key
    public <T> T getSession(String key,Class<T> c) {
        Jedis jedis = null;
        T res = null;
        try {
            jedis = jedisUtil.getJedis();
            String s = jedis.get(key);
            res = JSONObject.parseObject(s,c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
        return res;
    }

    public <T>boolean setSession(String key,T obj) {
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getJedis();
            String s = jedis.setex(key,Property.sessionExpireTime,
                    JSON.toJSONString(obj));
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
        return true;
    }

    public void removeSession(String sessionId) {
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getJedis();
            jedis.del(sessionId);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
    }
}
