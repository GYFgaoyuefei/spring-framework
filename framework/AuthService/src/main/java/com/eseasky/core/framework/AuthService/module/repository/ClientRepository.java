package com.eseasky.core.framework.AuthService.module.repository;

import org.springframework.data.repository.CrudRepository;

import com.eseasky.core.framework.AuthService.module.model.ServClientDetails;

public interface ClientRepository extends CrudRepository<ServClientDetails, Long>{
	ServClientDetails findByClientId(String clientId);
}
