package com.eseasky.core.framework.AuthService.module.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eseasky.core.framework.AuthService.module.model.AuthAccessToken;

public interface AuthAccessTokenRepository extends CrudRepository<AuthAccessToken, String>{
	void deleteByUserName(String  user_name);
	List<AuthAccessToken> findByUserName(String  user_name);
}
