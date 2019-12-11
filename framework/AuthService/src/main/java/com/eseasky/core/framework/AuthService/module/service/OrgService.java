package com.eseasky.core.framework.AuthService.module.service;

import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveVO;

public interface OrgService {

	Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO);
	
	OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO);

	OrgSaveVO updateOrg(OrgUpdateDTO orgUpdateDTO);

	OrgSaveVO disableOrg(OrgUpdateDTO orgUpdateDTO);

	OrgSaveVO openOrg(OrgUpdateDTO orgUpdateDTO);
	
	OrgSaveVO getOrgNameByOrgCode(String orgCode);
}
