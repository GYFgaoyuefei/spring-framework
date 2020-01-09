package com.eseasky.protocol.auth.protocol;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.GrantByGroupDTO;
import com.eseasky.protocol.auth.entity.DTO.ServUserInfoDTO;
import com.eseasky.protocol.auth.entity.VO.ServUserInfoVO;
import com.eseasky.protocol.auth.entity.VO.UserGrantInfoVO;

import feign.Headers;
import feign.RequestLine;


@Feign(serviceName="AuthService")
public interface GrantAndUserFeign {


	@RequestLine("POST /grantByGroup")
	@Headers({"Content-Type: application/json","Accept: application/json"})
    public ResultModel<UserGrantInfoVO> grantByGroup(@RequestBody @Validated GrantByGroupDTO grantByGroupDTO);
    
	@RequestLine("POST /queryUserInfo")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<List<ServUserInfoVO>> queryUserInfo(@RequestBody ServUserInfoDTO servUserInfoDTO);
	
	@RequestLine("POST /rejectByGroup")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<UserGrantInfoVO> rejectByGroup(@RequestBody @Validated GrantByGroupDTO groupRejectDTO);
}
