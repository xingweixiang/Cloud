package com.cloudnative.base.securityService.config;

import com.cloudnative.base.securityService.token.RedisTemplateTokenStore;
import com.cloudnative.base.securityService.token.ResJwtAccessTokenConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Assert;
import javax.sql.DataSource;

/**
* 类说明 
* redis存储token
*/
@Configuration
public class TokenStoreConfig  {



	@Bean
	@ConditionalOnProperty(prefix="security.oauth2.token.store",name="type" ,havingValue="jdbc" ,matchIfMissing=false)
	public JdbcTokenStore jdbcTokenStore( DataSource dataSource ){
 
//		oauth_access_token oauth_refresh_token 创建两张表
//		return new JdbcTokenStore( dataSource ) ;
		return new JdbcTokenStore( dataSource ) ;

	}
	@Bean
	@ConditionalOnProperty(prefix="security.oauth2.token.store",name="type" ,havingValue="redis" ,matchIfMissing=true)
	public RedisTemplateTokenStore redisTokenStore(RedisTemplate<String, Object>  redisTemplate ){
//		return new RedisTokenStore( redisTemplate.getConnectionFactory() ) ; //单台redis服务器
		Assert.state(redisTemplate != null, "RedisTemplate must be provided");

		RedisTemplateTokenStore redisTemplateStore = new RedisTemplateTokenStore()  ;
		redisTemplateStore.setRedisTemplate(redisTemplate);
		return redisTemplateStore ;
		 

	}
	
	//使用jwt替换原有的uuid生成token方式
	@Configuration
	@ConditionalOnProperty(prefix="security.oauth2.token.store",name="type" ,havingValue="jwt" ,matchIfMissing=false)
	public static class JWTTokenConfig {
		@Bean
		public JwtTokenStore jwtTokenStore(){
			return new JwtTokenStore( jwtAccessTokenConverter() ) ;
		}
		
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter(){
			JwtAccessTokenConverter accessTokenConverter = new ResJwtAccessTokenConverter();
			accessTokenConverter.setSigningKey("ocp");
			return accessTokenConverter ;
		}
	}
	
	
	
}
