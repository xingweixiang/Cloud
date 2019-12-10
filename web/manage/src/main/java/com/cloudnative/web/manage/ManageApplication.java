package com.cloudnative.web.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class ManageApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(ManageApplication.class, args);
	}
}
