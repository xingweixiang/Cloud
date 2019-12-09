package com.cloudnative.logService.service.impl;

import java.util.Date;

import com.cloudnative.base.dbService.datasource.annotation.DataSource;
import com.cloudnative.base.support.model.SysLog;
import com.cloudnative.logService.dao.LogDao;
import com.cloudnative.logService.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * 切换数据源，存储log-center
 */
public class LogServiceImpl implements LogService {

	@Autowired(required = false)
	private LogDao logDao;

	@Async
	@Override
	@DataSource(name="log")
	public void save(SysLog log) {
		if (log.getCreateTime() == null) {
			log.setCreateTime(new Date());
		}
		if (log.getFlag() == null) {
			log.setFlag(Boolean.TRUE);
		}

		logDao.save(log);
	}

	 
}
