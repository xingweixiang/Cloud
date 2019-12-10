package com.cloudnative.base.securityClient;

import javax.annotation.Resource;
import com.cloudnative.base.securityClient.authorize.AuthorizeConfigManager;
import com.cloudnative.base.support.auth.props.PermitUrlProperties;
import com.cloudnative.base.support.feign.FeignInterceptorConfig;
import com.cloudnative.base.support.feign.GolbalFeignConfig;
import com.cloudnative.base.support.rest.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Configuration
@EnableResourceServer
@AutoConfigureAfter(TokenStore.class)
@EnableConfigurationProperties(PermitUrlProperties.class)
@Import({RestTemplateConfig.class, FeignInterceptorConfig.class})
@EnableFeignClients(defaultConfiguration= GolbalFeignConfig.class)
//开启spring security 注解
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UAAClientAutoConfig extends ResourceServerConfigurerAdapter {

	// 对应oauth_client_details的 resource_ids字段 如果表中有数据
	// client_id只能访问响应resource_ids的资源服务器
	private static final String DEMO_RESOURCE_ID = "";

	@Resource
	private ObjectMapper objectMapper; // springmvc启动时自动装配json处理类

	@Resource
	private TokenStore redisTokenStore ;
	

	@Autowired(required = false)
	private JwtTokenStore jwtTokenStore;
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired(required = false)
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired(required = false)
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired(required = false)
	private AuthorizeConfigManager authorizeConfigManager;

	@Autowired(required = false)
	private OAuth2WebSecurityExpressionHandler expressionHandler;
	@Autowired(required = false)
	private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

	@Autowired(required = false)
	private PermitUrlProperties permitUrlProperties;

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(permitUrlProperties.getIgnored());
	}
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		if (jwtTokenStore != null) {
			resources.tokenStore(jwtTokenStore);
		} else if (redisTokenStore != null) {
			resources.tokenStore(redisTokenStore);
		}
		resources.stateless(true);

		resources.authenticationEntryPoint(authenticationEntryPoint);

		resources.expressionHandler(expressionHandler);
		resources.accessDeniedHandler(oAuth2AccessDeniedHandler);

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.headers().frameOptions().disable();

		authorizeConfigManager.config(http.authorizeRequests());

	}

}
