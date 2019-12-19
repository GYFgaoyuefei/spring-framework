package com.eseasky.protocol.auth.protocol;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.protocol.auth.entity.TokenVO;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Feign(serviceName="AuthService")
public interface AuthServiceFeign {
	
	@RequestLine("POST /oauth/token")
	@Headers("Content-Type: application/x-www-form-urlencoded")
	@Body("grant_type=password&client_id={client_id}&client_secret={client_secret}&password={password}&username={username}")
	public TokenVO tokenForPassword(@Param("client_id") String appId, @Param("client_secret") String clientSecret, @Param("username") String username, @Param("password") String password);
}
