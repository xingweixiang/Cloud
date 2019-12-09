package com.cloudnative.logService.annotation;

import com.cloudnative.logService.selector.LogImportSelector;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * 启动日志框架支持
 * 自动装配starter ，需要配置多数据源
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogImportSelector.class)
public @interface EnableLogging {
//	String name() ;
}