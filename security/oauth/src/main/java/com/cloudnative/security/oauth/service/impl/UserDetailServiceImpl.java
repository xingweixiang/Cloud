package com.cloudnative.security.oauth.service.impl;

import com.cloudnative.base.support.auth.details.LoginAppUser;
import com.cloudnative.base.support.util.StringUtils;
import com.cloudnative.logService.annotation.LogAnnotation;
import com.cloudnative.security.oauth.feign.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired(required = false)
    private UserFeignClient userFeignClient;

    @Override
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginAppUser loginAppUser = null;

        if (StringUtils.isPhone(username)){
            loginAppUser = userFeignClient.findByMobile(username);
        }else {
            //      后续考虑集成spring socail,支持多种类型登录
            loginAppUser = userFeignClient.findByUsername(username);   			  //方式1  feign调用       对外feign resttemplate
//        loginAppUser = userLoginGrpc.findByUsername(username);		  //方式2  gprc调用		对内grpc dubbo
        }

        if (loginAppUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if (!loginAppUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }

        return loginAppUser;
    }


     
}
