package com.cloudnative.security.oauth.feign;

import com.cloudnative.base.support.auth.details.LoginAppUser;
import com.cloudnative.base.support.feign.FeignExceptionConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* 调用用户中心中的userdetail对象，用户oauth中的登录
* 获取的用户与页面输入的密码 进行BCryptPasswordEncoder匹配
 */
@FeignClient(value="sys",configuration = FeignExceptionConfig.class)
public interface UserFeignClient {

	/**
	 * feign rpc访问远程/users-anon/login接口
	 * @param username
	 * @return
	 */
    @GetMapping(value = "/users-anon/login", params = "username")
	LoginAppUser findByUsername(@RequestParam("username") String username);


	@GetMapping(value = "/users-anon/mobile", params = "mobile")
	LoginAppUser findByMobile(@RequestParam("mobile") String mobile);

    
}
