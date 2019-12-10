package com.cloudnative.modules.sys.service.impl;

import com.cloudnative.base.support.exception.service.ServiceException;
import com.cloudnative.base.support.model.SysMenu;
import com.cloudnative.modules.sys.dao.SysMenuDao;
import com.cloudnative.modules.sys.dao.SysRoleMenuDao;
import com.cloudnative.modules.sys.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired(required = false)
	private SysMenuDao menuDao;
 	@Autowired(required = false)
	private SysRoleMenuDao roleMenuDao;

	@Transactional
	@Override
	public void save(SysMenu menu)  throws ServiceException{
		try {
			menu.setCreateTime(new Date());
			menu.setUpdateTime(menu.getCreateTime());

			menuDao.save(menu);
			log.info("新增菜单：{}", menu);
		} catch (Exception e) {
			throw new ServiceException(e) ;
		}
	}

	@Transactional
	@Override
	public void update(SysMenu menu)  throws ServiceException {
		try {
			menu.setUpdateTime(new Date());

			menuDao.updateByOps(menu);
			log.info("修改菜单：{}", menu);
		} catch (Exception e) {
			throw new ServiceException(e) ;
		}
	}

	@Transactional
	@Override
	public void delete(Long id)  throws ServiceException{
		try {
			SysMenu menu = menuDao.findById(id);

			menuDao.deleteByParentId(menu.getId());
			menuDao.delete(id);

			log.info("删除菜单：{}", menu);
		} catch (Exception e) {
			throw new ServiceException(e) ;
		}
	}

	@Transactional
	@Override
	public void setMenuToRole(Long roleId, Set<Long> menuIds)  throws ServiceException {
		try {
			roleMenuDao.delete(roleId, null);

			if (!CollectionUtils.isEmpty(menuIds)) {
				menuIds.forEach(menuId -> {
					roleMenuDao.save(roleId, menuId);
				});
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<SysMenu> findByRoles(Set<Long> roleIds)  throws ServiceException{
		try {
			return roleMenuDao.findMenusByRoleIds(roleIds);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<SysMenu> findAll()  throws ServiceException{
		try {
			return menuDao.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public SysMenu findById(Long id)  throws ServiceException{
		try {
			return menuDao.findById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Set<Long> findMenuIdsByRoleId(Long roleId)  throws ServiceException{
		try {
			return roleMenuDao.findMenuIdsByRoleId(roleId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<SysMenu> findOnes()  throws ServiceException{
		try {
			return menuDao.findOnes();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
