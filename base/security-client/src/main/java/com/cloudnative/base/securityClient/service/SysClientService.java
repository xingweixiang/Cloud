package com.cloudnative.base.securityClient.service;

import java.util.Map;

public interface SysClientService {

	public Map getClient(String clientId);
	
	public void loadAllClientToCache() ;
}
