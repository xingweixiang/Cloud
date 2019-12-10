package com.cloudnative.modules.sys.dao;

import com.cloudnative.base.support.model.SysMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Set;

/**
 * 角色菜单
 */
@Mapper
public interface SysRoleMenuDao {

	@Insert("insert into sys_role_menu(roleId, menuId) values(#{roleId}, #{menuId})")
	int save(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

	int delete(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

	@Select("select t.menuId from sys_role_menu t where t.roleId = #{roleId}")
	Set<Long> findMenuIdsByRoleId(Long roleId);

	List<SysMenu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
