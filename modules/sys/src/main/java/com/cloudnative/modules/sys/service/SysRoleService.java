package com.cloudnative.modules.sys.service;

import com.cloudnative.base.support.exception.service.ServiceException;
import com.cloudnative.base.support.model.SysPermission;
import com.cloudnative.base.support.model.SysRole;
import com.cloudnative.base.support.web.PageResult;
import com.cloudnative.base.support.web.Result;

import java.util.Map;
import java.util.Set;

public interface SysRoleService {

	/**
	 * 保存角色
	 * @param sysRole
	 */
	void save(SysRole sysRole)  throws ServiceException;

	/**
	 * 修改角色
	 * @param sysRole
	 */
	void update(SysRole sysRole)  throws ServiceException;

	/**
	 * 删除角色
	 * @param id
	 */
	void deleteRole(Long id)  throws ServiceException;

	/**
	 * 分配权限
	 * @param id
	 * @param permissionIds
	 */
	void setPermissionToRole(Long id, Set<Long> permissionIds)  throws ServiceException;

	/**
	 * ID获取角色
	 * @param id
	 * @return
	 */
	SysRole findById(Long id)  throws ServiceException;

	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	PageResult<SysRole> findRoles(Map<String, Object> params)  throws ServiceException;

	/**
	 * 角色权限列表
	 * @param roleId
	 * @return
	 */
	Set<SysPermission> findPermissionsByRoleId(Long roleId)  throws ServiceException;

	/**
	 * 更新角色
	 * @param sysRole
	 */
	Result saveOrUpdate(SysRole sysRole)  throws ServiceException;

}
