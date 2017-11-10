package com.rrd.plugin;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by xinghb on 2017/10/26.
 */
@Component
public class JedisHelper {

    @Autowired
    JedisConnectionFactory jedisConnectionFanctory;


    public void set(String key, String value, int seconds) throws Exception {
        RedisConnection redis = null;
        try {
            redis = jedisConnectionFanctory.getConnection();
            redis.set(key.getBytes("UTF-8"), value.getBytes("UTF-8"));
            redis.expire(key.getBytes(), seconds);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    public void setWithSec(String key, String value) throws Exception {
        RedisConnection redis = null;
        try {
            redis = jedisConnectionFanctory.getConnection();
            redis.set(key.getBytes("UTF-8"), value.getBytes("UTF-8"));
            redis.expire(key.getBytes(), 3600L);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    public String get(String key) throws Exception {
        RedisConnection redis = null;
        try {
            redis = jedisConnectionFanctory.getConnection();
            return new String(redis.get(key.getBytes("UTF-8")), "UTF-8");
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    public void setObj(String key, Object value, int seconds) throws Exception {
        RedisConnection redis = null;
        try {
            redis = jedisConnectionFanctory.getConnection();
            redis.set(key.getBytes("UTF-8"), JSON.toJSONString(value).getBytes("UTF-8"));
            redis.expire(key.getBytes(), seconds);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    public void setObjWithDay(String key, Object value, int seconds) {
        RedisConnection redis = null;
        try {
            redis = jedisConnectionFanctory.getConnection();
            redis.set(key.getBytes("UTF-8"), JSON.toJSONString(value).getBytes("UTF-8"));
            redis.expire(key.getBytes(), seconds * 24 * 3600);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    public <T> T getObj(String key, Class<T> clazz) throws Exception {
        RedisConnection redis = null;
        try {
            redis = jedisConnectionFanctory.getConnection();
            String value = new String(redis.get(key.getBytes("UTF-8")), "UTF-8");
            return JSON.parseObject(value, clazz);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    public void deleteByKey(String key) {
        RedisConnection redis = null;
        try {
            redis = jedisConnectionFanctory.getConnection();
            redis.del(key.getBytes());
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }
}
