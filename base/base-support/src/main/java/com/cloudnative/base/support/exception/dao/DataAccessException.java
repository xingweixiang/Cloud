package com.cloudnative.base.support.exception.dao;


import com.cloudnative.base.support.exception.BaseException;

/**
 * 数据库相关异常
 */

public class DataAccessException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8325096920926406459L;

	public DataAccessException(String msg) {
		super(msg);
	}

	public DataAccessException(Exception e) {
		this(e.getMessage());
	}
}
