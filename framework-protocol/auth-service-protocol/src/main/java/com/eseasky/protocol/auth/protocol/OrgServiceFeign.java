package com.eseasky.protocol.auth.protocol;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgNameQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.MulOrgsVO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;

import feign.Headers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Feign(serviceName="AuthService")
public interface OrgServiceFeign {


	@RequestMapping(value="/queryOrg",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
    public ResultModel<List<OrgQueryVO>> queryOrg(@RequestBody OrgQueryDTO orgQueryDTO);
    
	@RequestMapping(value="/saveOrg",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO saveOrg(@RequestBody @Validated OrgSaveDTO orgSaveDTO);
	
	@RequestMapping(value="/updateOrg",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO updateOrg(@RequestBody @Validated OrgUpByCodeDTO OrgUpdateDTO);

	@RequestMapping(value="/disableOrg",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO disableOrg(@RequestBody @Validated OrgQueryDTO orgUpdateDTO);
	
	@RequestMapping(value="/getOrgNameByOrgCode",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO getOrgNameByOrgCode(@RequestBody OrgQueryDTO orgQueryDTO);
	
	@RequestMapping(value="/getOrgNamesByOrgCodes",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public Map<String, String> getOrgNamesByOrgCodes(@RequestBody OrgNameQueryDTO orgNameQueryDTO);

	
	@RequestMapping(value="/queryOrgsByMerCode",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<List<MulOrgsVO>>  queryOrgsByMerCode(@RequestBody List<OrgQueryDTO> orgQueryDTOList);

	/**
	 * 查询(若未查出)并新增组织权限
	 * @param orgQueryDTOList
	 * @return
	 */
	@RequestMapping(value="/queryAndSaveOrg",method= RequestMethod.POST,consumes = "application/json")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<List<OrgQueryVO>> queryAndSaveOrg(@RequestBody List<OrgQueryDTO> orgQueryDTOList);
}
