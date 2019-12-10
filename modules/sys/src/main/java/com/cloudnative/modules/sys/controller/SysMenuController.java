package com.cloudnative.modules.sys.controller;

import com.cloudnative.base.support.auth.details.LoginAppUser;
import com.cloudnative.base.support.exception.controller.ControllerException;
import com.cloudnative.base.support.exception.service.ServiceException;
import com.cloudnative.base.support.model.SysMenu;
import com.cloudnative.base.support.model.SysRole;
import com.cloudnative.base.support.util.SysUserUtil;
import com.cloudnative.base.support.web.PageResult;
import com.cloudnative.base.support.web.Result;
import com.cloudnative.logService.annotation.LogAnnotation;
import com.cloudnative.modules.sys.service.SysMenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(tags = "MENU API")
@RequestMapping("/menus")
public class SysMenuController {

	@Autowired(required = false)
	private SysMenuService menuService;
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 删除菜单
	 * @param id
	 */
	@PreAuthorize("hasAuthority('menu:delete/menus/{id}')")
	@ApiOperation(value = "删除菜单")
	@DeleteMapping("/{id}")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result delete(@PathVariable Long id) throws ControllerException {

		try {
			try {
				menuService.delete(id);
				return Result.succeed("操作成功");
			} catch (Exception ex) {
				ex.printStackTrace();
				return Result.failed("操作失败");
			}
		} catch (Exception e) {
			throw new ControllerException(e);
		}

	}

	@PreAuthorize("hasAuthority('menu:get/menus/{roleId}/menus')")
	@ApiOperation(value = "根据roleId获取对应的菜单")
	@GetMapping("/{roleId}/menus")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public List<Map<String, Object>> findMenusByRoleId(@PathVariable Long roleId) throws ControllerException {

		try {
			Set<Long> roleIds = new HashSet<Long>()  ;
			//初始化角色
			roleIds.add(roleId) ;
			List<SysMenu> roleMenus = menuService.findByRoles(roleIds); // 获取该角色对应的菜单
			List<SysMenu> allMenus = menuService.findAll(); // 全部的菜单列表
			List<Map<String, Object>> authTrees = new ArrayList<>();

			Map<Long, SysMenu> roleMenusMap = roleMenus.stream()
					.collect(Collectors.toMap(SysMenu::getId, SysMenu -> SysMenu));

			for (SysMenu sysMenu : allMenus) {
				Map<String, Object> authTree = new HashMap<>();
				authTree.put("id", sysMenu.getId());
				authTree.put("name", sysMenu.getName());
				authTree.put("pId", sysMenu.getParentId());
				authTree.put("open", true);
				authTree.put("checked", false);
				if (roleMenusMap.get(sysMenu.getId()) != null) {
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
	 * 给角色分配菜单
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAuthority('menu:post/menus/granted')")
	@ApiOperation(value = "角色分配菜单")
	@PostMapping("/granted")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result setMenuToRole(@RequestBody SysMenu sysMenu) throws ControllerException {

		try {
			menuService.setMenuToRole(sysMenu.getRoleId(), sysMenu.getMenuIds());

			return Result.succeed("操作成功");
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}

	}

	@PreAuthorize("hasAuthority('menu:get/menus/findAlls')")
	@ApiOperation(value = "查询所有菜单")
	@GetMapping("/findAlls")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public PageResult<SysMenu> findAlls() throws ControllerException {

		try {
			List<SysMenu> list = menuService.findAll();

			return PageResult.<SysMenu>builder().data(list).code(0).count((long) list.size()).build();
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	@ApiOperation(value = "获取菜单以及顶级菜单")
	@GetMapping("/findOnes")
	@PreAuthorize("hasAuthority('menu:get/menus/findOnes')")
	public PageResult<SysMenu> findOnes() throws ControllerException {
		try {
			List<SysMenu> list = menuService.findOnes();
			return PageResult.<SysMenu>builder().data(list).code(0).count((long) list.size()).build();
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	/**
	 * 添加菜单 或者 更新
	 * 
	 * @param menu
	 * @return
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAnyAuthority('menu:post/menus','menu:put/menus')")
	@ApiOperation(value = "新增菜单")
	@PostMapping("saveOrUpdate")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public Result saveOrUpdate(@RequestBody SysMenu menu) throws ControllerException {

			try {
				if (menu.getId() != null) {
					menuService.update(menu);
				} else {
					menuService.save(menu);
				}
				return Result.succeed("操作成功");
			} catch (ServiceException e) {
				throw new ControllerException(e);
			}
		 

	}

	/**
	 * 当前登录用户的菜单
	 * 
	 * @return
	 * @throws ControllerException 
	 */
	@PreAuthorize("hasAuthority('menu:get/menus/current')")
	@GetMapping("/current")
	@ApiOperation(value = "查询当前用户菜单")
	@LogAnnotation(module="sys",recordRequestParam=false)
	public List<SysMenu> findMyMenu() throws ControllerException {

		try {
			LoginAppUser loginAppUser = SysUserUtil.getLoginAppUser();
			Set<SysRole> roles = loginAppUser.getSysRoles();
			if (CollectionUtils.isEmpty(roles)) {
				return Collections.emptyList();
			}

			List<SysMenu> menus = menuService
					.findByRoles(roles.parallelStream().map(SysRole::getId).collect(Collectors.toSet()));

			List<SysMenu> sysMenus = TreeBuilder(menus);

			return sysMenus;
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	/**
	 * 两层循环实现建树
	 * 
	 * @param sysMenus
	 * @return
	 * @throws ControllerException 
	 */
	public static List<SysMenu> TreeBuilder(List<SysMenu> sysMenus) throws ControllerException {

		try {
			List<SysMenu> menus = new ArrayList<SysMenu>();
			for (SysMenu sysMenu : sysMenus) {
				if (ObjectUtils.equals(-1L, sysMenu.getParentId())) {
					menus.add(sysMenu);
				}
				for (SysMenu menu : sysMenus) {
					if (menu.getParentId().equals(sysMenu.getId())) {
						if (sysMenu.getSubMenus() == null) {
							sysMenu.setSubMenus(new ArrayList<>());
						}
						sysMenu.getSubMenus().add(menu);
					}
				}
			}
			return menus;
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}

}
