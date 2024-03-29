package com.cloudnative.security.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.cloudnative.logService.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copy: [com.zzg]
 */
@Controller
@Api(tags = "REDIS API")
@RequestMapping("/redis")
public class RedisController {

    @Autowired(required = false)
    private RedisTemplate<String,Object> redisTemplate ;


   
    @ResponseBody
    @RequestMapping("/memoryInfo")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public String getMemoryInfo() {
        Map<String, Object> map = new HashMap<>();

        Object o = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.info("memory").get("used_memory");
            }
        });
        map.put("used_memory", o);
        map.put("create_time", System.currentTimeMillis());

        return JSON.toJSONString(map);
    }


    
    @ResponseBody
    @RequestMapping("/keysSize")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public String getKeysSize() {
        Map<String, Object> map = new HashMap<>();

        Object o =  redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });;
        map.put("dbSize", o);
        map.put("create_time", System.currentTimeMillis());

        return JSON.toJSONString(map);
    }

}
