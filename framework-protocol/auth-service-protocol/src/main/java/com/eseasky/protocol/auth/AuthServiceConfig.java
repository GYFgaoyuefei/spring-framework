package com.eseasky.protocol.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eseasky.core.starters.feign.wrapper.FeignClientFactory;
import com.eseasky.protocol.auth.protocol.AuthServiceFeign;
import com.eseasky.protocol.auth.protocol.OrgServiceFeign;

@ConditionalOnBean(FeignClientFactory.class)
@Configuration
public class AuthServiceConfig {
	@Autowired
	FeignClientFactory factory;

	@Bean
	public AuthServiceFeign authServiceFeign() {
		return factory.clientFeignContract(AuthServiceFeign.class);
	}
	
	@Bean
	public OrgServiceFeign orgServiceFeign() {
		return factory.clientFeignContract(OrgServiceFeign.class);
	}
}
