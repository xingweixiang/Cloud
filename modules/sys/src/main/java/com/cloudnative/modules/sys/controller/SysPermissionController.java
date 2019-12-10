package com.cloudnative.modules.sys.controller;

import com.cloudnative.base.support.exception.controller.ControllerException;
import com.cloudnative.base.support.exception.service.ServiceException;
import com.cloudnative.base.support.model.SysPermission;
import com.cloudnative.base.support.web.PageResult;
import com.cloudnative.base.support.web.Result;
import com.cloudnative.logService.annotation.LogAnnotation;
import com.cloudnative.modules.sys.service.SysPermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
* 权限管理
 */
@RestController
@Api(tags = "PERMISSION API")
public class SysPermissionController {

	@Autowired(required = false)
	private SysPermissionService sysPermissionService;

	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * 删除权限标识
	 * 参考 /permissions/1
	 * @param id
	 */
	@PreAuthorize("hasAuthority('permission:delete/permissions/{id}')")
	@ApiOperation(value = "后台管理删除权限标识")
	@DeleteMapping("permission:delete/permissions/{id}")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result delete(@PathVariable Long id) throws ControllerException {

		try {
			sysPermissionService.delete(id);
			return  Result.succeed("操作成功");
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}

	}


	/**
	 * 查询所有的权限标识
	 * 参考 ?start=0&length=10
	 * @return
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAuthority('permission:get/permissions')")
	@ApiOperation(value = "后台管理查询所有的权限标识")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页起始位置", required = true, dataType = "Integer"),
        @ApiImplicitParam(name = "limit",value = "分页结束位置", required = true, dataType = "Integer")
    })
	@LogAnnotation(module="sys",recordRequestParam=false)
	@GetMapping("/permissions")
	public PageResult<SysPermission> findPermissions(@RequestParam Map<String, Object> params) throws ControllerException {
		
		try {
			return sysPermissionService.findPermissions(params);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	/**
	 * 权限新增或者更新
	 * @param sysPermission
	 * @return
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAnyAuthority('permission:put/permissions','permission:post/permissions')")
	@PostMapping("/permissions/saveOrUpdate")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result saveOrUpdate(@RequestBody SysPermission sysPermission) throws ControllerException {
		try{
			if (sysPermission.getId()!=null){
				sysPermissionService.update(sysPermission);
			}else {
				sysPermissionService.save(sysPermission);
			}
			return Result.succeed("操作成功");
		}catch (ServiceException e){
			throw new ControllerException(e);
		}
	}

	@PreAuthorize("hasAuthority('permission:get/permissions/{roleId}/permissions')")
	@ApiOperation(value = "根据roleId获取对应的权限")
	@GetMapping("/permissions/{roleId}/permissions")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public List<Map<String, Object>> findAuthByRoleId(@PathVariable Long roleId) throws ControllerException {
		
		try {
			List<Map<String, Object>> authTrees = new ArrayList<>();
			Set<Long> roleIds = new HashSet<Long>();
			//初始化角色
			roleIds.add(roleId);
			Set<SysPermission> roleAuths = sysPermissionService.findByRoleIds(roleIds);//根据roleId获取对应的权限
			PageResult<SysPermission> allAuths = sysPermissionService.findPermissions(null);//根据roleId获取对应的权限


			Map<Long,SysPermission> roleAuthsMap = roleAuths.stream().collect(Collectors.toMap(SysPermission::getId,SysPermission->SysPermission));

			for (SysPermission sysPermission : allAuths.getData() ){
				Map<String, Object> authTree = new HashMap<>();
				authTree.put("id",sysPermission.getId() + "");
				authTree.put("name",sysPermission.getName());
				authTree.put("open",true);
				authTree.put("checked", false);
				if (roleAuthsMap.get(sysPermission.getId())!=null){
					authTree.put("checked", true);
				}
				authTrees.add(authTree);
			}

			return authTrees;
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}
	/**
	 * 给角色分配权限
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAuthority('permission:post/permissions/granted')")
	@ApiOperation(value = "角色分配权限")
	@PostMapping("/permissions/granted")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result setAuthToRole(@RequestBody SysPermission sysPermission) throws ControllerException {
		try {
			sysPermissionService.setAuthToRole(sysPermission.getRoleId(),sysPermission.getAuthIds());
			return Result.succeed("操作成功");
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}












}
