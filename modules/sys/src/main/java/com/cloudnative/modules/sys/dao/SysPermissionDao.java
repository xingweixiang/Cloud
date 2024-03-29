package com.cloudnative.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudnative.base.support.model.SysPermission;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * 权限
 */
@Mapper
public interface SysPermissionDao  extends BaseMapper<SysPermission> {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_permission(permission, name, createTime, updateTime) values(#{permission}, #{name}, #{createTime}, #{createTime})")
	int save(SysPermission sysPermission);

	@Update("update sys_permission t set t.name = #{name}, t.permission = #{permission}, t.updateTime = #{updateTime} where t.id = #{id}")
	int updateByOps(SysPermission sysPermission);

	@Delete("delete from sys_permission where id = #{id}")
	int deleteOps(Long id);

	@Select("select * from sys_permission t where t.id = #{id}")
	SysPermission findById(Long id);

	@Select("select * from sys_permission t where t.permission = #{permission}")
	SysPermission findByPermission(String permission);

	int count(Map<String, Object> params);

	List<SysPermission> findList(Map<String, Object> params);

}
