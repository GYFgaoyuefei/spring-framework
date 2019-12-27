package com.eseasky.core.framework.AuthService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveMoreDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveByExcelVO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.VO.MulOrgsVO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(value = "组织管理", tags = "组织管理服务")
@RestController
@Log4j2
@RequestMapping("/OrgManage")
public class OrgController {
	
	@Autowired
	private OrgService orgService;
	
	@ApiOperation(value = "查询组织", httpMethod = "POST")
    @PostMapping(value = "/queryOrg")
    public ResultModel<List<OrgQueryVO>> queryOrg(@RequestBody OrgQueryDTO orgQueryDTO) {

        ResultModel<List<OrgQueryVO>> msgReturn = new ResultModel<List<OrgQueryVO>>();
        Page<OrgQueryVO> orgQueryVOs = orgService.queryOrg(orgQueryDTO);
        log.info(JSONObject.toJSONString(orgQueryVOs));
        msgReturn.setData(orgQueryVOs.getContent(),MsgPageInfo.loadFromPageable(orgQueryVOs));
        return msgReturn;
    }
	
	@ApiOperation(value = "添加组织", httpMethod = "POST")
	@PostMapping(value = "/saveOrg")
	public ResultModel<OrgSaveVO> saveOrg(@RequestBody @Validated OrgSaveDTO orgSaveDTO) {

		ResultModel<OrgSaveVO> msgReturn = new ResultModel<OrgSaveVO>();
		OrgSaveVO orgSaveVO = orgService.saveOrg(orgSaveDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		msgReturn.setData(orgSaveVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "添加组织通过Excel", httpMethod = "POST")
	@PostMapping(value = "/saveOrgByExcel")
	public ResultModel<OrgSaveByExcelVO> saveOrgByExcel( OrgSaveMoreDTO orgSaveMoreDTO) {

		ResultModel<OrgSaveByExcelVO> msgReturn = new ResultModel<OrgSaveByExcelVO>();
		OrgSaveByExcelVO orgSaveVO = orgService.saveByExcel(orgSaveMoreDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		msgReturn.setData(orgSaveVO);
		return msgReturn;
	}
	
//	@ApiOperation(value = "添加多个组织", httpMethod = "POST")
//	@PostMapping(value = "/saveMoreOrg")
//	public ResultModel<Set<OrgSaveVO>> saveMoreOrg(@RequestBody @Validated OrgSaveMoreDTO orgSaveDTO) {
//
//		ResultModel<Set<OrgSaveVO>> msgReturn = new ResultModel<Set<OrgSaveVO>>();
//		Set<OrgSaveVO> orgSaveVOs = orgService.saveMoreOrg(orgSaveDTO);
//		log.info(JSONObject.toJSONString(orgSaveVOs));
//		msgReturn.setData(orgSaveVOs);
//		return msgReturn;
//	}
	
	@ApiOperation(value = "更新组织", httpMethod = "POST")
	@PostMapping(value = "/updateOrg")
	public ResultModel<OrgSaveVO> updateOrg(@RequestBody @Validated OrgUpdateDTO orgUpdateDTO) {

		ResultModel<OrgSaveVO> msgReturn = new ResultModel<OrgSaveVO>();
		OrgSaveVO orgSaveVO = orgService.updateOrg(orgUpdateDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		msgReturn.setData(orgSaveVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "禁用组织", httpMethod = "POST")
	@PostMapping(value = "/disableOrg")
	public ResultModel<OrgSaveVO> disableOrg(@RequestBody @Validated OrgQueryDTO orgUpdateDTO) {

		ResultModel<OrgSaveVO> msgReturn = new ResultModel<OrgSaveVO>();
		OrgSaveVO orgSaveVO = orgService.disableOrg(orgUpdateDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		msgReturn.setData(orgSaveVO);
		return msgReturn;
	}

	@ApiOperation(value = "解禁组织", httpMethod = "POST")
	@PostMapping(value = "/openOrg")
	public ResultModel<OrgSaveVO> openOrg(@RequestBody  @Validated OrgQueryDTO orgUpdateDTO) {

		ResultModel<OrgSaveVO> msgReturn = new ResultModel<OrgSaveVO>();
		OrgSaveVO orgSaveVO = orgService.openOrg(orgUpdateDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		msgReturn.setData(orgSaveVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "查询多级组织", httpMethod = "POST")
	@PostMapping(value = "/queryMulOrgs")
	public ResultModel<List<MulOrgsVO>>queryMulOrgs(@RequestBody OrgQueryDTO orgQueryDTO) {

		ResultModel<List<MulOrgsVO>> msgReturn = new ResultModel<List<MulOrgsVO>>();
		List<MulOrgsVO> mulOrgsVOs = orgService.queryMulOrgs(orgQueryDTO);
		log.info(JSONObject.toJSONString(mulOrgsVOs));
		msgReturn.setData(mulOrgsVOs);
		return msgReturn;
	}
}
