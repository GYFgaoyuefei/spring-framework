package com.eseasky.core.framework.AuthService.module.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.MulOrgsVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveVO;

public interface OrgService {

	Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO);
	
	OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO);

	OrgSaveVO updateOrg(OrgUpdateDTO orgUpdateDTO);

	OrgSaveVO disableOrg(OrgQueryDTO orgUpdateDTO);

	OrgSaveVO openOrg(OrgQueryDTO orgUpdateDTO);
	
	OrgSaveVO getOrgNameByOrgCode(String orgCode);

	List<MulOrgsVO> queryMulOrgs();

	OrgSaveVO checkOrgName(OrgSaveDTO orgSaveDTO);
}
