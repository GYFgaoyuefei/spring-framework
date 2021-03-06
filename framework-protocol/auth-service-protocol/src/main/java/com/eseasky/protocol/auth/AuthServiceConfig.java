package com.eseasky.protocol.auth;

import com.eseasky.protocol.auth.protocol.OrgRefreshServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eseasky.core.starters.feign.wrapper.FeignClientFactory;
import com.eseasky.protocol.auth.protocol.AuthServiceFeign;
import com.eseasky.protocol.auth.protocol.GrantAndUserFeign;
import com.eseasky.protocol.auth.protocol.OrgServiceFeign;
import com.eseasky.protocol.auth.protocol.proxy.OrgCacheService;
import com.eseasky.protocol.auth.protocol.proxy.OrgRefreshService;

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
		return new OrgCacheService(factory.client(OrgServiceFeign.class));
	}

	@Bean
	public OrgRefreshServiceFeign orgRefreshServiceFeign() {
		return new OrgRefreshService(factory.client(OrgRefreshServiceFeign.class));
	}
	
	@Bean
	public GrantAndUserFeign grantAndUserFeign() {
		return factory.clientFeignContract(GrantAndUserFeign.class);
	}
}
