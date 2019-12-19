package com.eseasky.protocol.auth.protocol;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;

import feign.Headers;
import feign.RequestLine;


@Feign(serviceName="AuthService")
public interface OrgServiceFeign {
	

	@RequestLine("POST /queryOrg")
	@Headers({"Content-Type: application/json","Accept: application/json"})
    public Page<OrgQueryVO> queryOrg(@RequestBody OrgQueryDTO orgQueryDTO);
    
	@RequestLine("POST /saveOrg")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO saveOrg(@RequestBody @Validated OrgSaveDTO orgSaveDTO);
}
