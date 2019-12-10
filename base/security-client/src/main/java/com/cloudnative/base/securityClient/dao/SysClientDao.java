package com.cloudnative.base.securityClient.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 查询应用绑定的资源权限
 */
@Mapper
public interface SysClientDao {

	 
	@Select("select * from oauth_client_details t where t.client_id = #{clientId}")
	Map getClient(String clientId);
	
	
	@Select("select * from oauth_client_details t where status=1 ")
	List<Map> findAll();

 
}
