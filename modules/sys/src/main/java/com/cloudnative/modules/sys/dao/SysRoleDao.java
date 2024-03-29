package com.cloudnative.modules.sys.dao;

import com.cloudnative.base.support.model.SysRole;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@Mapper
public interface SysRoleDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_role(code, name, createTime, updateTime) values(#{code}, #{name}, #{createTime}, #{createTime})")
	int save(SysRole sysRole);

	@Update("update sys_role t set t.name = #{name} ,t.updateTime = #{updateTime} where t.id = #{id}")
	int updateByOps(SysRole sysRole);

	@Select("select * from sys_role t where t.id = #{id}")
	SysRole findById(Long id);

	@Select("select * from sys_role t where t.code = #{code}")
	SysRole findByCode(String code);

	@Delete("delete from sys_role where id = #{id}")
	int delete(Long id);

	int count(Map<String, Object> params);

	List<SysRole> findList(Map<String, Object> params);

}
