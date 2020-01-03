package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.service.CacheService;
import com.eseasky.core.framework.AuthService.protocol.dto.CacheRemoveDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.CacheRemoveVO;
import com.eseasky.core.starters.auth.cache.RedisSecurityCacheManager;

import com.google.common.base.Strings;

@Service
public class CacheServiceImpl implements CacheService {
	
	@Autowired
	private RedisSecurityCacheManager redisSecurityCacheManager;
	
	@Override
	public void removeCache(CacheRemoveDTO cacheRemoveDTO) {
		// TODO Auto-generated method stub
		if(cacheRemoveDTO==null || Strings.isNullOrEmpty(cacheRemoveDTO.getCacheName())) {
			redisSecurityCacheManager.behaviour().clear();
		}else {
			if(!Strings.isNullOrEmpty(cacheRemoveDTO.getKey())) {
				redisSecurityCacheManager.behaviour().remove(cacheRemoveDTO.getCacheName(),cacheRemoveDTO.getKey(),cacheRemoveDTO.getIsMatchStart());
			}else {
				redisSecurityCacheManager.behaviour().remove(cacheRemoveDTO.getCacheName());
			}
		}		
	}
	
	@Override
	public CacheRemoveVO getKey(CacheRemoveDTO cacheRemoveDTO) {
		// TODO Auto-generated method stub
		CacheRemoveVO cacheRemoveVO=null;
		if(cacheRemoveDTO!=null) {
			Set<String>keys=redisSecurityCacheManager.behaviour().keys(cacheRemoveDTO.getCacheName(), cacheRemoveDTO.getKey(), cacheRemoveDTO.getIsMatchStart());
			if(keys!=null) {
				cacheRemoveVO=new CacheRemoveVO();
				cacheRemoveVO.setKeys(keys);
			}
		}	
		return cacheRemoveVO;
	}
	

}
