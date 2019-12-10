package com.cloudnative.base.securityClient.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface RbacService {
	
	boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
