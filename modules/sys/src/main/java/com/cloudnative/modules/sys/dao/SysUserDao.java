package com.cloudnative.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudnative.base.support.model.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@Mapper
public interface SysUserDao  extends BaseMapper<SysUser> {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, sex, enabled, type, createTime, updateTime) "
			+ "values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{sex}, #{enabled}, #{type}, #{createTime}, #{updateTime})")
	int save(SysUser sysUser);

	int updateByOps(SysUser sysUser);

	@Select("select * from sys_user t where t.username = #{username}")
	SysUser findByUsername(String username);

	@Select("select * from sys_user t where t.id = #{id}")
	SysUser findById(Long id);

	int count(Map<String, Object> params);

	List<SysUser> findList(Map<String, Object> params);
	
	
	@Select("select u.* from sys_user u   where u.username = #{username}")
	SysUser findUserByUsername(String username);

	@Select("select u.* from sys_user u   where u.phone = #{mobile}")
	SysUser findUserByMobile(String mobile);
	

}
