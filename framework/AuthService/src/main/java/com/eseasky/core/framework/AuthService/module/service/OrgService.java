package com.eseasky.core.framework.AuthService.module.service;

import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveMoreDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveByExcelVO;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.MulOrgsVO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface OrgService {

	Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO);
	
	OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO);

	OrgSaveVO updateOrg(OrgUpdateDTO orgUpdateDTO);

	OrgSaveVO disableOrg(OrgQueryDTO orgUpdateDTO);

	OrgSaveVO openOrg(OrgQueryDTO orgUpdateDTO);
	
	OrgSaveVO getOrgNameByOrgCode(String orgCode);

	OrgSaveVO checkOrgName(OrgSaveDTO orgSaveDTO);

	List<MulOrgsVO> queryMulOrgs(OrgQueryDTO orgQueryDTO);

	OrgSaveVO updateOrgByCode(OrgUpByCodeDTO orgUpdateDTO);

	OrgSaveByExcelVO saveByExcel(OrgSaveMoreDTO orgSaveMoreDTO);

	OrgSaveVO saveForApp(OrgSaveDTO orgSaveDTO);

	List<MulOrgsVO> queryOrgsByMerCode(Set<String> orgCodeList);

	List<OrgQueryVO> queryAndSaveOrg(List<OrgQueryDTO> orgQueryDTOList);

	Map<String, String> getOrgNamesByOrgCodes(Set<String> orgCodes);
}
