package com.cloudnative.security.oauth.service;

import com.cloudnative.base.support.web.PageResult;
import com.cloudnative.base.support.web.Result;
import com.cloudnative.security.oauth.dto.SysClientDto;
import com.cloudnative.security.oauth.model.SysClient;

import java.util.List;
import java.util.Map;

public interface SysClientService {

	
	SysClient getById(Long id) ;
	 

    Result saveOrUpdate(SysClientDto clientDto);

    void deleteClient(Long id);
    
    public PageResult<SysClient> listRoles(Map<String, Object> params);
    
    List<SysClient> findList(Map<String, Object> params) ;
    

	Result updateEnabled(Map<String, Object> params);
    
}
