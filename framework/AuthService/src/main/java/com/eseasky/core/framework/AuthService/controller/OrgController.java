package com.eseasky.core.framework.AuthService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveVO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;

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
	public ResultModel<OrgSaveVO> saveOrg(@RequestBody OrgSaveDTO orgSaveDTO) {

		ResultModel<OrgSaveVO> msgReturn = new ResultModel<OrgSaveVO>();
		OrgSaveVO orgSaveVO = orgService.saveOrg(orgSaveDTO);
		log.info(JSONObject.toJSONString(orgSaveVO));
		msgReturn.setData(orgSaveVO);
		return msgReturn;
	}

}
