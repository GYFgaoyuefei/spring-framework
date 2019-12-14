package com.eseasky.core.framework.AuthService.module.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eseasky.core.framework.AuthService.module.model.ServLoginCode;

@Repository
public interface LoginCodeRepository extends CrudRepository<ServLoginCode, Long>{
	ServLoginCode findByPhone(String phone);
}
