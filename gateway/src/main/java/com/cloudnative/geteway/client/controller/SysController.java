package com.cloudnative.geteway.client.controller;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RandomUtil;
import com.cloudnative.base.securityClient.token.RedisTemplateTokenStore;
import com.cloudnative.base.support.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class SysController {
	
	@Resource
	private RedisTemplate< String, Object> redisTemplate ;
	
	@GetMapping("/hello")
	@PreAuthorize("hasAuthority('sys:user:add11')")
	public String hello() {
		redisTemplate.opsForValue().set("hello", "owen");
		return "hello";
	}

	@RequestMapping(value = { "/users" }, produces = "application/json") // 获取用户信息。/auth/user
	public Map<String, Object> user(OAuth2Authentication user) {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("user", user.getUserAuthentication().getPrincipal());
		log.debug("认证详细信息:" + user.getUserAuthentication().getPrincipal().toString());
		userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
		return userInfo;
	}
	
	@RequestMapping(value = { "/user" }, produces = "application/json") // 获取用户信息。/auth/user
    public Principal user(Principal user) {
        return user;
    }
	
	
	@GetMapping("/del/{accessToken}/{refreshToken}")
	public String hello2(@PathVariable String accessToken,@PathVariable String refreshToken) {
		RedisTemplateTokenStore redisTemplateStore = new RedisTemplateTokenStore();
		redisTemplateStore.setRedisTemplate(redisTemplate);
		redisTemplateStore.removeAccessToken(accessToken);
		redisTemplateStore.removeRefreshToken(refreshToken);
		return "delR";
	}

	@GetMapping("/getVersion")
	public Result token() {
		String str = RandomUtil.randomString(24);
		StrBuilder token = new StrBuilder();
		token.append(str);
		redisTemplate.opsForValue().set(token.toString(), token.toString(),300);
		return Result.succeed(token.toString(),"");
	}


}
