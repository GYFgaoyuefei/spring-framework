package com.eseasky.protocol.auth.protocol;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;


@Feign(serviceName="AuthService")
public interface OrgServiceFeign {
	

    @PostMapping(value = "/queryOrg")
    public Page<OrgQueryVO> queryOrg(@RequestBody OrgQueryDTO orgQueryDTO);
    
	@PostMapping(value = "/saveOrg")
	public OrgSaveVO saveOrg(@RequestBody @Validated OrgSaveDTO orgSaveDTO);
}
