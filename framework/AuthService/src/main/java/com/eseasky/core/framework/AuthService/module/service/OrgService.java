package com.eseasky.core.framework.AuthService.module.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveMoreDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.MulOrgsVO;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;


public interface OrgService {

	Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO);
	
	OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO);

	OrgSaveVO updateOrg(OrgUpdateDTO orgUpdateDTO);

	OrgSaveVO disableOrg(OrgQueryDTO orgUpdateDTO);

	OrgSaveVO openOrg(OrgQueryDTO orgUpdateDTO);
	
	OrgSaveVO getOrgNameByOrgCode(String orgCode);

	OrgSaveVO checkOrgName(OrgSaveDTO orgSaveDTO);

	List<MulOrgsVO> queryMulOrgs(OrgQueryDTO orgQueryDTO);

	Set<OrgSaveVO> saveMoreOrg(OrgSaveMoreDTO orgSaveDTO);

	OrgSaveVO updateOrgByCode(OrgUpByCodeDTO orgUpdateDTO);

}
