package com.cloudnative.base.support.feign;

import feign.Logger.Level;
import org.springframework.context.annotation.Bean;

public class GolbalFeignConfig {

	@Bean
	public Level levl(){
		return Level.FULL;
	}
}
