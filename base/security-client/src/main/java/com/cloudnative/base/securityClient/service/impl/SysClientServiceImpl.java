package com.cloudnative.base.securityClient.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.cloudnative.base.securityClient.dao.SysClientDao;
import com.cloudnative.base.securityClient.service.SysClientService;
import com.cloudnative.base.support.constant.UaaConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 查询应用绑定的资源权限
 */
@Slf4j
@Service("sysClientService")
public class SysClientServiceImpl implements SysClientService {

 
    @Autowired(required = false)
    private RedisTemplate<String,Object> redisTemplate ;
    @Autowired(required = false)
    private SysClientDao sysClientDao ;
    
	public Map getClient(String clientId){
		// 先从redis获取
		Map client ;
        String value = (String) redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).get(clientId);
        if (StringUtils.isBlank(value)) {
        	client = cacheAndGetClient(clientId);
        } else {
        	client = JSONObject.parseObject(value, Map.class);
        }
        return client ;
	}
	
	/**
     * 缓存client并返回client
     *
     * @param clientId
     * @return
     */
    private Map cacheAndGetClient(String clientId) {
        // 从数据库读取
    	Map client = null ;
        try {
        	client = sysClientDao.getClient(clientId);
            if (client != null) {
                // 写入redis缓存
                redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).put(clientId, JSONObject.toJSONString(client));
                log.info("缓存clientId:{},{}", clientId, client);
            }
        }catch (NoSuchClientException e){
        	log.info("clientId:{},{}", clientId, clientId );
        }catch (InvalidClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return client;
    }
	
    /**
     * 将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache() {
        if (redisTemplate.hasKey(UaaConstant.CACHE_CLIENT_KEY)) {
            return;
        }
        log.info("将oauth_client_details全表刷入redis");

        List<Map> list = sysClientDao.findAll();
        if (CollectionUtils.isEmpty(list)) {
        	log.error("oauth_client_details表数据为空，请检查");
            return;
        }

        for(Iterator<Map> it = list.iterator() ; it.hasNext();){
        	Map temp = it.next() ;
        	redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).put(String.valueOf(temp.get("client_id")), JSONObject.toJSONString(temp));
        }
        
    }
 
}
