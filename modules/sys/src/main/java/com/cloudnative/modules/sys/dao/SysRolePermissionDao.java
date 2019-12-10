package com.cloudnative.modules.sys.dao;

import com.cloudnative.base.support.model.SysPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Set;


/**
 * 角色权限关系
 */
@Mapper
public interface SysRolePermissionDao {

	@Insert("insert into sys_role_permission(roleId, permissionId) values(#{roleId}, #{permissionId})")
	int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	int deleteRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	Set<SysPermission> findPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);

}
