package com.cloudnative.base.support.feign;

import lombok.Data;

@Data
public class FeignFaildResult {
	
	 private int status;
	 private String resp_msg;
}
