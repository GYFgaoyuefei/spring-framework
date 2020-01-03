package com.eseasky.core.framework.AuthService.module.service;

import com.eseasky.core.framework.AuthService.protocol.dto.CacheRemoveDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.CacheRemoveVO;

public interface CacheService {

	public void removeCache(CacheRemoveDTO cacheRemoveDTO);

	public CacheRemoveVO getKey(CacheRemoveDTO cacheRemoveDTO);

	

}
