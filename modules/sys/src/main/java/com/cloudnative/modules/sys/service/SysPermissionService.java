package com.cloudnative.modules.sys.service;

import com.cloudnative.base.support.exception.service.ServiceException;
import com.cloudnative.base.support.model.SysPermission;
import com.cloudnative.base.support.web.PageResult;

import java.util.Map;
import java.util.Set;

public interface SysPermissionService {

	/**
	 * 根绝角色ids获取权限集合
	 * @param roleIds
	 * @return
	 */
	Set<SysPermission> findByRoleIds(Set<Long> roleIds)  throws ServiceException;

	/**
	 * 保存权限
	 * @param sysPermission
	 */
	void save(SysPermission sysPermission)  throws ServiceException;

	/**
	 * 修改权限
	 * @param sysPermission
	 */
	void update(SysPermission sysPermission)  throws ServiceException;

	/**
	 * 删除权限
	 * @param id
	 */
	void delete(Long id)  throws ServiceException;

	/**
	 * 权限列表
	 * @param params
	 * @return
	 */
	PageResult<SysPermission> findPermissions(Map<String, Object> params)  throws ServiceException;

	/**
	 * 授权
	 * @param roleId
	 * @param authIds
	 */
	void setAuthToRole(Long roleId, Set<Long> authIds)  throws ServiceException;

}
