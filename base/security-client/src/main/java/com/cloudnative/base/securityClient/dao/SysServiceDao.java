package com.cloudnative.base.securityClient.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 查询应用绑定的资源权限
 */
@Mapper
public interface SysServiceDao {

	@Select("select p.* from sys_service p inner join sys_client_service rp on p.id = rp.serviceId where rp.clientId = #{clientId} order by p.sort")
	List<Map> listByClientId(Long clientId);

}
