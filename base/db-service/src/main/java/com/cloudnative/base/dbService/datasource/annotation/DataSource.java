package com.cloudnative.base.dbService.datasource.annotation;

import java.lang.annotation.*;


/**
 * 数据源选择
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	//数据库名称
    String name();
}