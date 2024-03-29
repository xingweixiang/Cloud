package com.cloudnative.geteway.client.utils;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;


//http://blog.csdn.net/lzy_lizhiyang/article/details/47951423
public class RateLimiterUtils {  
    
      
    private static final ConcurrentHashMap<String, RateLimiter> resourceLimitMap =   
            new ConcurrentHashMap<String, RateLimiter>();  
      
      
    /** 
     * 限流 
     * @param resource 需要限流的对象的标识 
     * @return true表示得到了许可，没有达到限流阀值，false表示得不到许可，达到了限流阀值。 
     * @author: hejinen 
     * @date:2016年10月19日 上午11:08:49 
     */  
    public static boolean rateLimit(String resource) {  
        RateLimiter limit = getRateLimit(resource);  
        return limit.tryAcquire();  
    }  
        /** 
     * 获取某个需限流对象的RateLimiter，如不存在则创建新的 
     * @param resouce 需要限流的对象标识 
     */  
    public static RateLimiter getRateLimit(String resource) {  
        RateLimiter limit = resourceLimitMap.get(resource);   
        if(limit == null) {  
            synchronized(RateLimiterUtils.class) {  
                limit = resourceLimitMap.get(resource);  
                //double qps = getQpsByResource(resource);
                double qps = 0.0;
                if(limit == null) {  
                    limit = RateLimiter.create(qps);  
                    resourceLimitMap.put(resource, limit);  
                    //LoggerUtil.info(RateLimiterUtils.class, "create rate limiter for key:{0},QPS:{1}", resource,qps);  
                }  
            }  
        }  
        return resourceLimitMap.get(resource);  
    }  
}  