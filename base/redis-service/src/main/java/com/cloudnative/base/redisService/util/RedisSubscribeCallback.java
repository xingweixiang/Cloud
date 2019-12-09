package com.cloudnative.base.redisService.util;

 
public interface RedisSubscribeCallback {
    void callback(String msg);
}
