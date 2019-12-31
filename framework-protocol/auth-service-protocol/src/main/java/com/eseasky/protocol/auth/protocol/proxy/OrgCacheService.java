package com.eseasky.protocol.auth.protocol.proxy;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgNameQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.MulOrgsVO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgServiceFeign;

public class OrgCacheService implements OrgServiceFeign {
	private OrgServiceFeign feign;
	
	public OrgCacheService(OrgServiceFeign feign) {
		this.feign = feign;
	}
	
	@Override
	@Cacheable(value = {"org_code_defined_list"})
	public ResultModel<List<OrgQueryVO>> queryOrg(OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		return feign.queryOrg(orgQueryDTO);
	}

	@Override
//	@CachePut(value = {"org_code_defined_top"}, key="'OrgSaveVO'+#result.orgCode")
	@CacheEvict(value = {"org_code_defined_list","org_code_defined_top"}, allEntries = true)
	public OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO) {
		// TODO Auto-generated method stub
		return feign.saveOrg(orgSaveDTO);
	}

	@Override
//	@CachePut(value = {"org_code_defined_top"}, key="'OrgSaveVO'+#result.orgCode")
	@CacheEvict(value = {"org_code_defined_list","org_code_defined_top"}, allEntries = true)
	public OrgSaveVO updateOrg(OrgUpByCodeDTO OrgUpdateDTO) {
		// TODO Auto-generated method stub
		return feign.updateOrg(OrgUpdateDTO);
	}

	@Override
//	@CachePut(value = {"org_code_defined_top"}, key="'OrgSaveVO'+#result.orgCode")
	@CacheEvict(value = {"org_code_defined_list","org_code_defined_top"}, allEntries = true)
	public OrgSaveVO disableOrg(OrgQueryDTO orgUpdateDTO) {
		// TODO Auto-generated method stub
		return feign.disableOrg(orgUpdateDTO);
	}

	@Override
	@Cacheable(value = {"org_code_defined_top"}, key="'OrgSaveVO'+#orgQueryDTO.orgCode")
	@CacheEvict(value = {"org_code_defined_list"}, allEntries = true)
	public OrgSaveVO getOrgNameByOrgCode(OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		return feign.getOrgNameByOrgCode(orgQueryDTO);
	}

	@Override
	public Map<String, String> getOrgNamesByOrgCodes(OrgNameQueryDTO orgNameQueryDTO) {
		// TODO Auto-generated method stub
		return feign.getOrgNamesByOrgCodes(orgNameQueryDTO);
	}

	@Override
	@Cacheable(value = {"org_code_defined_list"})
	public ResultModel<List<MulOrgsVO>> queryOrgsByMerCode(List<OrgQueryDTO> orgQueryDTOList) {
		// TODO Auto-generated method stub
		return feign.queryOrgsByMerCode(orgQueryDTOList);
	}

	@Override
	public ResultModel<List<OrgQueryVO>> queryAndSaveOrg(List<OrgQueryDTO> orgQueryDTOList) {
		// TODO Auto-generated method stub
		return feign.queryAndSaveOrg(orgQueryDTOList);
	}

}
