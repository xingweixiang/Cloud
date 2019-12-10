package com.cloudnative.modules.sys;

import com.cloudnative.base.support.annotation.EnableApiIdempotent;
import com.cloudnative.base.support.port.PortApplicationEnvironmentPreparedEventListener;
import com.cloudnative.logService.annotation.EnableLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
* 启动类
*/
 
@Configuration
@EnableLogging
@EnableDiscoveryClient
@SpringBootApplication
@EnableApiIdempotent
public class SysApp {
	
	public static void main(String[] args) {
//		固定端口启动
//		SpringApplication.run(SysApp.class, args);
		
		//随机端口启动
		SpringApplication app = new SpringApplication(SysApp.class);
        app.addListeners(new PortApplicationEnvironmentPreparedEventListener());
        app.run(args);
		
	}

}
