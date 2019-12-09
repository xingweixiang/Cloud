package com.cloudnative.security.oauth.controller;

import com.cloudnative.base.support.web.PageResult;
import com.cloudnative.base.support.web.Result;
import com.cloudnative.logService.annotation.LogAnnotation;
import com.cloudnative.security.oauth.dto.SysClientDto;
import com.cloudnative.security.oauth.model.SysService;
import com.cloudnative.security.oauth.service.SysServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(tags = "SERVICE API")
@RequestMapping("/services")
public class SysServiceController {

    @Autowired(required = false)
    private SysServiceService sysServiceService;

    /**
     * 查询所有服务
     * @return
     */
    @ApiOperation(value = "查询所有服务")
    @GetMapping("/findAlls")
    @PreAuthorize("hasAuthority('service:get/service/findAlls')")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public PageResult<SysService> findAlls() {
        List<SysService> list = sysServiceService.findAll();

        return PageResult.<SysService>builder().data(list).code(0).count((long)list.size()).build() ;
    }

    /**
     * 获取服务以及顶级服务
     * @return
     */
    @ApiOperation(value = "获取服务以及顶级服务")
    @GetMapping("/findOnes")
    @PreAuthorize("hasAuthority('service:get/service/findOnes')")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public PageResult<SysService> findOnes(){
        List<SysService> list = sysServiceService.findOnes();
        return PageResult.<SysService>builder().data(list).code(0).count((long)list.size()).build() ;
    }

    /**
     * 删除服务
     * @param id
     * @return
     */
    
    
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除服务")
    @PreAuthorize("hasAuthority('service:delete/service/{id}')")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public Result delete(@PathVariable Long id){
        try {
            sysServiceService.delete(id);

        }catch (Exception ex){
            ex.printStackTrace();
            return Result.failed("操作失败");
        }
        return Result.succeed("操作成功");
    }

    
    @ApiOperation(value = "新增服务")
    @PostMapping("/saveOrUpdate")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    @PreAuthorize("hasAnyAuthority('service:post/saveOrUpdate')")
    public Result saveOrUpdate(@RequestBody SysService service) {
        try{
            if (service.getId() != null){
                sysServiceService.update(service);
            }else {
                sysServiceService.save(service);
            }
            return Result.succeed("操作成功");
        }catch (Exception ex){
            ex.printStackTrace();
            return Result.failed("操作失败");
        }
    }

    @ApiOperation(value = "根据clientId获取对应的服务")
    @GetMapping("/{clientId}/services")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public List<Map<String, Object>> findServicesByclientId(@PathVariable Long clientId) {
        Set<Long> clientIds = new HashSet<Long>();
        
        //初始化应用
        clientIds.add(clientId);
        
        List<SysService> clientService = sysServiceService.findByClient(clientIds);
        List<SysService> allService = sysServiceService.findAll();
        List<Map<String, Object>> authTrees = new ArrayList<>();

        Map<Long,SysService> clientServiceMap = clientService.stream().collect(Collectors.toMap(SysService::getId,SysService->SysService));

        for (SysService sysService: allService) {
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id",sysService.getId());
            authTree.put("name",sysService.getName());
            authTree.put("pId",sysService.getParentId());
            authTree.put("open",true);
            authTree.put("checked", false);
            if (clientServiceMap.get(sysService.getId())!=null){
                authTree.put("checked", true);
            }
            authTrees.add(authTree);
        }

        return  authTrees;
    }

    @PostMapping("/granted")
    @LogAnnotation(module="oauth",recordRequestParam=false)
    public Result setMenuToClient(@RequestBody SysClientDto clientDto) {
        sysServiceService.setMenuToClient(clientDto.getId(), clientDto.getServiceIds());

        return Result.succeed("操作成功");
    }















}
