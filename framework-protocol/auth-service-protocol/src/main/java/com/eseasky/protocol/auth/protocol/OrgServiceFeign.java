package com.eseasky.protocol.auth.protocol;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.MulOrgsVO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;

import feign.Headers;
import feign.RequestLine;
import io.swagger.annotations.ApiOperation;

import java.util.List;


@Feign(serviceName="AuthService")
public interface OrgServiceFeign {
	

	@RequestLine("POST /queryOrg")
	@Headers({"Content-Type: application/json","Accept: application/json"})
    public ResultModel<List<OrgQueryVO>> queryOrg(@RequestBody OrgQueryDTO orgQueryDTO);
    
	@RequestLine("POST /saveOrg")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO saveOrg(@RequestBody @Validated OrgSaveDTO orgSaveDTO);
	
	@RequestLine("POST /updateOrg")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO updateOrg(@RequestBody @Validated OrgUpByCodeDTO OrgUpdateDTO);
	
	@RequestLine("POST /disableOrg")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO disableOrg(@RequestBody @Validated OrgQueryDTO orgUpdateDTO);
	
	@RequestLine("POST /getOrgNameByOrgCode")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public OrgSaveVO getOrgNameByOrgCode(@RequestBody OrgQueryDTO orgQueryDTO);

	
	@RequestLine("POST /queryOrgsByMerCode")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<List<MulOrgsVO>>  queryOrgsByMerCode(@RequestBody List<OrgQueryDTO> orgQueryDTOList);

	/**
	 * 查询(若未查出)并新增组织权限
	 * @param orgQueryDTOList
	 * @return
	 */
	@RequestLine("POST /queryAndSaveOrg")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<List<OrgQueryVO>> queryAndSaveOrg(@RequestBody List<OrgQueryDTO> orgQueryDTOList);
}
