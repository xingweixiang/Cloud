package com.cloudnative.modules.sys.dao;

import com.cloudnative.base.support.model.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Set;


/**
* 用户角色关系
 */
@Mapper
public interface SysUserRoleDao {

	int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

	@Insert("insert into sys_role_user(userId, roleId) values(#{userId}, #{roleId})")
	int saveUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

	/**
	 * 根据用户id获取角色
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select r.* from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.userId = #{userId}")
	Set<SysRole> findRolesByUserId(Long userId);

	/**
	 *  根据用户ids 获取
	 * @param userIds
	 * @return
	 */
	@Select("<script>select r.*,ru.userId from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.userId IN " +
			" <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
			" #{item} " +
			" </foreach>" +
			"</script>")
	List<SysRole> findRolesByUserIds(List<Long> userIds);



}
