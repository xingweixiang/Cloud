package com.cloudnative.security.oauth.dao;

import com.cloudnative.security.oauth.model.SysService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Set;

@Mapper
public interface SysClientServiceDao {


    @Insert("insert into sys_client_service(clientId, serviceId) values(#{clientId}, #{serviceId})")
    int save(@Param("clientId") Long clientId, @Param("serviceId") Long serviceId);

    int delete(@Param("clientId") Long clientId, @Param("serviceId") Long serviceId);

    @Select("select t.serviceId from sys_client_service t where t.clientId = #{clientId}")
    Set<Long> findServiceIdsByClientId(Long clientId);

    List<SysService> findServicesBySlientIds(@Param("clientIds") Set<Long> clientIds);


}
