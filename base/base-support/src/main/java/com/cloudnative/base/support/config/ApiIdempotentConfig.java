package com.cloudnative.base.support.config;

import com.cloudnative.base.redisService.util.RedisUtil;
import com.cloudnative.base.support.interceptor.AccessLimitInterceptor;
import com.cloudnative.base.support.interceptor.ApiIdempotentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;
/**
 * WebMvcConfigurer 拦截器
 */
@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class ApiIdempotentConfig implements  WebMvcConfigurer {


    @Resource
    private RedisTemplate< String, Object> redisTemplate ;

    @Autowired(required = false)
	private RedisUtil redisUtil;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new AccessLimitInterceptor(redisUtil)) ;
        registry.addInterceptor(new ApiIdempotentInterceptor(redisTemplate)).addPathPatterns("/**") ;
        
        
    }
}
