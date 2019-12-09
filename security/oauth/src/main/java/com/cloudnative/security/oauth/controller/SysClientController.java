package com.cloudnative.security.oauth.controller;

import com.cloudnative.base.support.web.PageResult;
import com.cloudnative.base.support.web.Result;
import com.cloudnative.logService.annotation.LogAnnotation;
import com.cloudnative.security.oauth.dto.SysClientDto;
import com.cloudnative.security.oauth.model.SysClient;
import com.cloudnative.security.oauth.service.SysClientService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色相关接口
 */
@RestController
@Api(tags = "CLIENT API")
@RequestMapping("/clients")
public class SysClientController {

    @Autowired(required = false)
    private SysClientService sysClientService;


    @GetMapping
    @ApiOperation(value = "应用列表")
    @PreAuthorize("hasAuthority('client:get/clients')")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public PageResult<SysClient> listRoles(@RequestParam Map<String, Object> params) {
        return sysClientService.listRoles(params) ;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取应用")
    @PreAuthorize("hasAuthority('client:get/clients/{id}')")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public SysClient get(@PathVariable Long id) {
        return sysClientService.getById(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有应用")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    @PreAuthorize("hasAnyAuthority('client:get/clients')")
    public List<SysClient> roles() {
        return sysClientService.findList(Maps.newHashMap());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除应用")
    @PreAuthorize("hasAuthority('client:delete/clients')")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public void delete(@PathVariable Long id) {
    	sysClientService.deleteClient(id);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存或者修改应用")
    @PreAuthorize("hasAuthority('client:post/clients')")
    public Result saveOrUpdate(@RequestBody SysClientDto clientDto){
        return  sysClientService.saveOrUpdate(clientDto);
    }
    
    @PutMapping("/updateEnabled")
    @ApiOperation(value = "修改状态")
    @PreAuthorize("hasAuthority('client:post/clients')")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public Result updateEnabled(@RequestBody Map<String, Object> params){
        return  sysClientService.updateEnabled(params);
    }
    
}
