package com.eseasky.core.framework.AuthService.module.repository;

import org.springframework.data.repository.CrudRepository;

import com.eseasky.core.framework.AuthService.module.model.AuthAccessToken;
import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoDTO;

public interface AuthAccessTokenRepository extends CrudRepository<AuthAccessToken, Long>{
	void deleteByUserName(String  userName);
}
