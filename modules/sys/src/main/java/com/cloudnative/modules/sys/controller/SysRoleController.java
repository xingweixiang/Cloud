package com.cloudnative.modules.sys.controller;

import com.cloudnative.base.support.exception.controller.ControllerException;
import com.cloudnative.base.support.exception.service.ServiceException;
import com.cloudnative.base.support.model.SysRole;
import com.cloudnative.base.support.web.PageResult;
import com.cloudnative.base.support.web.Result;
import com.cloudnative.logService.annotation.LogAnnotation;
import com.cloudnative.modules.sys.service.SysRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
* 角色管理
 */

@RestController
@Api(tags = "ROLE API")
public class SysRoleController {

	@Autowired(required = false)
	private SysRoleService sysRoleService;
	private ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * 后台管理查询角色
	 * @param params
	 * @return
	 * @throws JsonProcessingException 
	 */
	@PreAuthorize("hasAuthority('role:get/roles')")
	@ApiOperation(value = "后台管理查询角色")
	@GetMapping("/roles")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public PageResult<SysRole> findRoles(@RequestParam Map<String, Object> params) throws ControllerException {
		try {
			return sysRoleService.findRoles(params);
		} catch (ServiceException e) {
			 throw new ControllerException(e);
		}
	}

	/**
	 * 角色新增或者更新
	 * @param sysRole
	 * @return
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAnyAuthority('role:post/roles','role:put/roles')")
	@PostMapping("/roles/saveOrUpdate")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result saveOrUpdate(@RequestBody SysRole sysRole) throws ControllerException {
		try {
			return sysRoleService.saveOrUpdate(sysRole);
		} catch (ServiceException e) {
			 throw new ControllerException(e);
		}
	}

	/**
	 * 后台管理删除角色
	 * delete /role/1
	 * @param id
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAuthority('role:delete/roles/{id}')")
	@ApiOperation(value = "后台管理删除角色")
	@DeleteMapping("/roles/{id}")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result deleteRole(@PathVariable Long id) throws ControllerException {
		try {
			if (id == 1L){
				return Result.failed("管理员不可以删除");
			}
			sysRoleService.deleteRole(id);
			return Result.succeed("操作成功");
		}catch (Exception e){
			 throw new ControllerException(e);
		}
	}

}
