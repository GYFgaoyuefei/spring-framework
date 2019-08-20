package com.eseasky.core.framework.AuthService.module.repository;

import org.springframework.data.repository.CrudRepository;

import com.eseasky.core.framework.AuthService.module.model.AuthAccessToken;
import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoDTO;

import java.util.List;

public interface AuthAccessTokenRepository extends CrudRepository<AuthAccessToken, Long>{
	void deleteByUserName(String  user_name);
	List<AuthAccessToken> findByUserName(String  user_name);
}
