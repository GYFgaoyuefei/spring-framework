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
import com.eseasky.core.framework.AuthService.module.service.PowerService;
import com.eseasky.core.framework.AuthService.protocol.dto.AuPowerGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.VO.VRInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(value = "角色管理", tags = "角色管理服务")
@RestController
@Log4j2
@RequestMapping("/PowerManage")
public class PowerController {
	
	@Autowired
	private PowerService groupService;
	
	@ApiOperation(value = "添加vr", httpMethod = "POST")
	@PostMapping(value = "/createPower")
	public ResultModel<PowerSaveVO> createPower(@RequestBody  @Validated PowerSaveDTO groupSaveDTO) {

		ResultModel<PowerSaveVO> msgReturn = new ResultModel<PowerSaveVO>();
		PowerSaveVO groupSaveVO = groupService.createPower(groupSaveDTO);
		log.info(JSONObject.toJSONString(groupSaveVO));
		msgReturn.setData(groupSaveVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "更新vr", httpMethod = "POST")
	@PostMapping(value = "/updatePower")
	public ResultModel<PowerSaveVO> updatePower(@RequestBody  @Validated PowerUpdateDTO groupUpdateDTO) {

		ResultModel<PowerSaveVO> msgReturn = new ResultModel<PowerSaveVO>();
		PowerSaveVO groupSaveVO = groupService.updatePower(groupUpdateDTO);
		log.info(JSONObject.toJSONString(groupSaveVO));
		msgReturn.setData(groupSaveVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "删除vr", httpMethod = "POST")
	@PostMapping(value = "/deletePower")
	public ResultModel<PowerSaveVO> deletePower(@RequestBody PowerUpdateDTO groupUpdateDTO) {
		ResultModel<PowerSaveVO> msgReturn = new ResultModel<PowerSaveVO>();
		PowerSaveVO groupSaveVO = groupService.deletePower(groupUpdateDTO);
		log.info(JSONObject.toJSONString(groupSaveVO));
		msgReturn.setData(groupSaveVO);
		return msgReturn;
		
	}
	
	@ApiOperation(value = "查询vr", httpMethod = "POST")
    @PostMapping(value = "/queryPower")
    public ResultModel<List<PowerQueryVO>> queryPower(@RequestBody PowerQueryDTO groupQueryDTO) {

        ResultModel<List<PowerQueryVO>> msgReturn = new ResultModel<List<PowerQueryVO>>();
        Page<PowerQueryVO> groupQueryVOs = groupService.queryPower(groupQueryDTO);
        log.info(JSONObject.toJSONString(groupQueryVOs));
        msgReturn.setData(groupQueryVOs.getContent(),MsgPageInfo.loadFromPageable(groupQueryVOs));
        return msgReturn;
    }
	
	@ApiOperation(value = "vr授权", httpMethod = "POST")
	@PostMapping(value = "/grant")
	public ResultModel<OrgGrantInfoVO> grant(@RequestBody @Validated AuPowerGrantDTO groupGrantDTO) {

		ResultModel<OrgGrantInfoVO> msgReturn = new ResultModel<OrgGrantInfoVO>();
		OrgGrantInfoVO orgGrantInfoVO = groupService.grant(groupGrantDTO);
		log.info(JSONObject.toJSONString(orgGrantInfoVO));
		msgReturn.setData(orgGrantInfoVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "查询授权资源", httpMethod = "POST")
    @PostMapping(value = "/queryResoure")
    public ResultModel<List<ResoureQueryVO>> queryResoure(@RequestBody ResoureQueryDTO resoureQueryDTO) {

        ResultModel<List<ResoureQueryVO>> msgReturn = new ResultModel<List<ResoureQueryVO>>();
        Page<ResoureQueryVO> resoureQueryVOs = groupService.queryResoureItem(resoureQueryDTO);
        log.info(JSONObject.toJSONString(resoureQueryVOs));
        if(resoureQueryVOs!=null)
        	msgReturn.setData(resoureQueryVOs.getContent(),MsgPageInfo.loadFromPageable(resoureQueryVOs));
        return msgReturn;
    }
	
	@ApiOperation(value = "vr授权删除", httpMethod = "POST")
    @PostMapping(value = "/reject")
    public ResultModel<VRInfoVO> reject(@RequestBody @Validated VRInfoDTO vRInfoDTO ) {

        ResultModel<VRInfoVO> msgReturn = new ResultModel<VRInfoVO>();
        VRInfoVO vRInfoVO = groupService.reject(vRInfoDTO);
        log.info(JSONObject.toJSONString(vRInfoVO));
        if(vRInfoVO!=null)
        	msgReturn.setData(vRInfoVO);
        log.info(System.currentTimeMillis());
        return msgReturn;
    }

}
