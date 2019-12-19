package com.eseasky.protocol.auth.protocol.hystrix;

import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;
import com.eseasky.protocol.auth.entity.TokenVO;
import com.eseasky.protocol.auth.protocol.AuthServiceFeign;

public class AuthServiceFeignHystrix implements AuthServiceFeign {

	@Override
	public TokenVO tokenForPassword(String appId, String clientSecret, String username, String password) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, "error");
	}

}
