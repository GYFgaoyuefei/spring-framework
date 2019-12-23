package com.eseasky.core.framework.AuthService.controller;


import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgServiceFeign;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Api(value = "组织管理", tags = "组织管理服务")
@RestController
@Log4j2
public class OrgFeignController implements OrgServiceFeign {
	
	@Autowired
	private OrgService orgService;

    @Override
	@ApiOperation(value = "查询组织", httpMethod = "POST")
    @PostMapping(value = "/queryOrg",consumes = "application/json")
    public ResultModel<List<OrgQueryVO>> queryOrg(@RequestBody OrgQueryDTO orgQueryDTO) {
		ResultModel<List<OrgQueryVO>> resultModel = new ResultModel<>();
		Page<OrgQueryVO> orgQueryVOS = orgService.queryOrg(orgQueryDTO);
		if(orgQueryVOS!=null){
			MsgPageInfo msgPageInfo = new MsgPageInfo();
			msgPageInfo.setSize(orgQueryVOS.getPageable().getPageSize());
			msgPageInfo.setTotal(orgQueryVOS.getTotalElements());
			msgPageInfo.setPage(orgQueryVOS.getPageable().getPageNumber());
			resultModel.setData(orgQueryVOS.getContent(),msgPageInfo);
		}
		return resultModel;
    }


	@Override
	@ApiOperation(value = "添加组织", httpMethod = "POST")
	@PostMapping(value = "/saveOrg",consumes = "application/json")
	public OrgSaveVO saveOrg(@RequestBody @Validated OrgSaveDTO orgSaveDTO) {

		OrgSaveVO orgSaveVO = orgService.saveForApp(orgSaveDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		return orgSaveVO;
	}

	@PostMapping(value = "/updateOrg",consumes = "application/json")
	public OrgSaveVO updateOrg(@RequestBody @Validated OrgUpByCodeDTO OrgUpdateDTO) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = orgService.updateOrgByCode(OrgUpdateDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		return orgSaveVO;
	}

	@PostMapping(value = "/disableOrg",consumes = "application/json")
	public OrgSaveVO disableOrg(@RequestBody @Validated OrgQueryDTO orgUpdateDTO) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = orgService.disableOrg(orgUpdateDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		return orgSaveVO;
	}

	@PostMapping(value = "/getOrgNameByOrgCode",consumes = "application/json")
	public OrgSaveVO getOrgNameByOrgCode(@RequestBody OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = orgService.getOrgNameByOrgCode(orgQueryDTO.getOrgCode());
		log.info(JSONObject.toJSONString(orgSaveVO));
		return orgSaveVO;
	}

}
