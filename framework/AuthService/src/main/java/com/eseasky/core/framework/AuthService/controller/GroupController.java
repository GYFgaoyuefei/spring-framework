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
import com.eseasky.core.framework.AuthService.module.service.GroupService;
import com.eseasky.core.framework.AuthService.module.service.PowerService;
import com.eseasky.core.framework.AuthService.protocol.dto.AuPowerGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.QueryGroupDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.PowerUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.PowerSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.UserGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.VRInfoVO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(value = "角色管理", tags = "角色管理服务")
@RestController
@Log4j2
@RequestMapping("/GroupManage")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@ApiOperation(value = "添加权限分组", httpMethod = "POST")
	@PostMapping(value = "/addGroup")
	public ResultModel<GroupSaveVO> addGroup(@RequestBody  @Validated GroupSaveDTO groupSaveDTO) {

		ResultModel<GroupSaveVO> msgReturn = new ResultModel<GroupSaveVO>();
		GroupSaveVO groupSaveVO = groupService.addGroup(groupSaveDTO);
		log.info(JSONObject.toJSONString(groupSaveVO));
		msgReturn.setData(groupSaveVO);
		return msgReturn;
	}
	
//	@ApiOperation(value = "更新权限分组", httpMethod = "POST")
//	@PostMapping(value = "/updatePower")
//	public ResultModel<PowerSaveVO> updatePower(@RequestBody  @Validated PowerUpdateDTO groupUpdateDTO) {
//
//		ResultModel<PowerSaveVO> msgReturn = new ResultModel<PowerSaveVO>();
//		PowerSaveVO groupSaveVO = groupService.updatePower(groupUpdateDTO);
//		log.info(JSONObject.toJSONString(groupSaveVO));
//		msgReturn.setData(groupSaveVO);
//		return msgReturn;
//	}
	
	@ApiOperation(value = "删除权限分组", httpMethod = "POST")
	@PostMapping(value = "/deleteGroup")
	public ResultModel<GroupQueryVO> deleteGroup(@RequestBody QueryGroupDTO groupUpdateDTO) {
		ResultModel<GroupQueryVO> msgReturn = new ResultModel<GroupQueryVO>();
		groupService.deleteGroup(groupUpdateDTO);
		msgReturn.setData(null);
		return msgReturn;
		
	}
	
	@ApiOperation(value = "查询权限分组", httpMethod = "POST")
    @PostMapping(value = "/queryGroup")
    public ResultModel<List<GroupQueryVO>> queryGroup(@RequestBody QueryGroupDTO groupQueryDTO) {

        ResultModel<List<GroupQueryVO>> msgReturn = new ResultModel<List<GroupQueryVO>>();
        List<GroupQueryVO> groupQueryVOs = groupService.queryGroup(groupQueryDTO);
        log.info(JSONObject.toJSONString(groupQueryVOs));
        msgReturn.setData(groupQueryVOs);
        return msgReturn;
    }
	
	@ApiOperation(value = "授权", httpMethod = "POST")
	@PostMapping(value = "/grantByGroup")
	public ResultModel<UserGrantInfoVO> grantByGroup(@RequestBody @Validated GroupGrantDTO groupGrantDTO) {

		ResultModel<UserGrantInfoVO> msgReturn = new ResultModel<UserGrantInfoVO>();
		UserGrantInfoVO orgGrantInfoVO = groupService.grantByGroup(groupGrantDTO);
		log.info(JSONObject.toJSONString(orgGrantInfoVO));
		msgReturn.setData(orgGrantInfoVO);
		return msgReturn;
	}
	
	

}
