package com.cloudnative.logService.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * log-spring-boot-starter 自动装配
 */


public class LogImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// TODO Auto-generated method stub
//		importingClassMetadata.getAllAnnotationAttributes(EnableEcho.class.getName());

		return new String[] { 
				"com.cloudnative.logService.aop.LogAnnotationAOP",
				"com.cloudnative.logService.service.impl.LogServiceImpl",
				"com.cloudnative.logService.config.LogAutoConfig"
				
		};
	}

}