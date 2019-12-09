package com.cloudnative.base.support.feign;

import com.cloudnative.base.support.exception.hystrix.HytrixException;
import com.cloudnative.base.support.exception.service.ServiceException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import java.io.IOException;

@Slf4j
public class FeignExceptionConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }
    /**
     * 重新实现feign的异常处理，捕捉restful接口返回的json格式的异常信息
     * 
     */
    public class UserErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
        	 Exception exception = null;
             ObjectMapper mapper = new ObjectMapper();
             //空属性处理
             mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
             //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
             mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
             //禁止使用int代表enum的order来反序列化enum
             mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
             try {
                 String json = Util.toString(response.body().asReader());
                 // 非业务异常包装成自定义异常类ServiceException
                 if (StringUtils.isNotEmpty(json)) {
                	 
                	 if(json.contains("resp_code")){
                		 FeignFaildResult result = mapper.readValue(json, FeignFaildResult.class);
                         result.setStatus(response.status());
                         // 业务异常包装成自定义异常类HytrixException
                         if (result.getStatus() != HttpStatus.OK.value()) {
                             exception = new HytrixException(result.getResp_msg());
                         }else{
                        	 exception = feign.FeignException.errorStatus(methodKey, response);
                         }
                	 }else{
                		 exception = new ServiceException("程序异常");
                	 }
                	 
                    
                 }else{
                	 exception = feign.FeignException.errorStatus(methodKey, response);
                 }
                 
             } catch (IOException ex) {
                 log.error(ex.getMessage(), ex);
             }
             return exception;
        }

	 
    }
}