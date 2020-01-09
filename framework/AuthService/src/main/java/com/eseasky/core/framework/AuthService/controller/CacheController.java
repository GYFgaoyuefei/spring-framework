package com.eseasky.core.framework.AuthService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eseasky.core.framework.AuthService.module.service.CacheService;
import com.eseasky.core.framework.AuthService.protocol.dto.CacheRemoveDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.CacheRemoveVO;import com.eseasky.global.entity.ResultModel;

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
	public ResultModel<CacheRemoveVO> getKey(@RequestBody CacheRemoveDTO cacheRemoveDTO) {
		// TODO Auto-generated method stub
        ResultModel<CacheRemoveVO> msgReturn = new ResultModel<CacheRemoveVO>();
        CacheRemoveVO cacheRemoveVO=cacheService.getKey(cacheRemoveDTO);
        if(cacheRemoveVO!=null) {
        	msgReturn.setData(cacheRemoveVO);
        }
		return  msgReturn;
	}
}
