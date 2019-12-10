package com.cloudnative.modules.sys.dao;

import com.cloudnative.base.support.model.SysMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 菜单
 */
@Mapper
public interface SysMenuDao {

	@Insert("insert into sys_menu(parentId, name, url, path, css, sort, createTime, updateTime,isMenu,hidden) "
			+ "values (#{parentId}, #{name}, #{url} , #{path} , #{css}, #{sort}, #{createTime}, #{updateTime},#{isMenu},#{hidden})")
	int save(SysMenu menu);

	int updateByOps(SysMenu menu);

	@Select("select * from sys_menu t where t.id = #{id}")
	SysMenu findById(Long id);

	@Delete("delete from sys_menu where id = #{id}")
	int delete(Long id);

	@Delete("delete from sys_menu where parentId = #{id}")
	int deleteByParentId(Long id);

	@Select("select * from sys_menu t order by t.sort")
	List<SysMenu> findAll();

	@Select("select * from sys_menu t where t.isMenu = 1 order by t.sort")
	List<SysMenu> findOnes();
}
