package com.cloudnative.base.securityService.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * 开启session共享
*/
@EnableRedisHttpSession
public class SessionConfig {

}
