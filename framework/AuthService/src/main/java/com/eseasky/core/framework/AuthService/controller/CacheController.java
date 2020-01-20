package com.eseasky.core.framework.AuthService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eseasky.core.framework.AuthService.module.service.CacheService;
import com.eseasky.core.framework.AuthService.protocol.dto.CacheRemoveDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.CacheRemoveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "缓存管理", tags = "缓存管理服务")
@RestController
@RequestMapping("/cacheManage")
public class CacheController{
	
	@Autowired
	private CacheService cacheService;
	
	@ApiOperation(value = "清除缓存", httpMethod = "POST")
    @PostMapping(value = "/removeCache")
    public void removeCache(@RequestBody  CacheRemoveDTO cacheRemoveDTO) {       
        cacheService.removeCache(cacheRemoveDTO);
    }


	@ApiOperation(value = "模糊查询缓存key值", httpMethod = "POST")
	@PostMapping(value = "/getKey")
	public CacheRemoveVO getKey(@RequestBody CacheRemoveDTO cacheRemoveDTO) {
		// TODO Auto-generated method stub
		return  cacheService.getKey(cacheRemoveDTO);
	}
	
	@ApiOperation(value = "清除缓存(带固定前缀)", httpMethod = "POST")
    @PostMapping(value = "/removeCacheByCacheModule")
    public void removeCacheByCacheModule(@RequestBody  CacheRemoveDTO cacheRemoveDTO) {       
        cacheService.removeCacheByCacheModule(cacheRemoveDTO);
    }


	@ApiOperation(value = "模糊查询缓存key值(带固定前缀)", httpMethod = "POST")
	@PostMapping(value = "/getKeyByCacheModule")
	public CacheRemoveVO getKeyByCacheModule(@RequestBody CacheRemoveDTO cacheRemoveDTO) {
		// TODO Auto-generated method stub
		return  cacheService.getKeyByCacheModule(cacheRemoveDTO);
	}
}
