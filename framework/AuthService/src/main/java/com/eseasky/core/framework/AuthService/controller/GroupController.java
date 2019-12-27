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
import com.eseasky.core.framework.AuthService.protocol.dto.AddPow2GroupDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.QueryGroupDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.UserGrantInfoVO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(value = "权限分组管理", tags = "权限分组管理服务")
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
	
	@ApiOperation(value = "删除权限分组子项", httpMethod = "POST")
	@PostMapping(value = "/deleteGroup")
	public ResultModel<GroupQueryVO> deletePowerOfGroup(@RequestBody QueryGroupDTO groupUpdateDTO) {
		ResultModel<GroupQueryVO> msgReturn = new ResultModel<GroupQueryVO>();
		groupService.deletePowerOfGroup(groupUpdateDTO);
		msgReturn.setData(null);
		return msgReturn;
		
	}
	
	@ApiOperation(value = "查询权限分组", httpMethod = "POST")
    @PostMapping(value = "/queryGroup")
    public ResultModel<List<GroupQueryVO>> queryGroup(@RequestBody QueryGroupDTO groupQueryDTO) {

        ResultModel<List<GroupQueryVO>> msgReturn = new ResultModel<List<GroupQueryVO>>();
        Page<GroupQueryVO> groupQueryVOs = groupService.queryGroup(groupQueryDTO);
        if(groupQueryVOs!=null) {
        	msgReturn.setData(groupQueryVOs.getContent(), MsgPageInfo.loadFromPageable(groupQueryVOs));
        }
        log.info(JSONObject.toJSONString(groupQueryVOs));
        return msgReturn;
    }
	
	@ApiOperation(value = "通过分组授权", httpMethod = "POST")
	@PostMapping(value = "/grantByGroup")
	public ResultModel<UserGrantInfoVO> grantByGroup(@RequestBody @Validated GroupGrantDTO groupGrantDTO) {

		ResultModel<UserGrantInfoVO> msgReturn = new ResultModel<UserGrantInfoVO>();
		UserGrantInfoVO orgGrantInfoVO = groupService.grantByGroup(groupGrantDTO);
		log.info(JSONObject.toJSONString(orgGrantInfoVO));
		msgReturn.setData(orgGrantInfoVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "添加权限分组子项", httpMethod = "POST")
	@PostMapping(value = "/addPowerToGroup")
	public ResultModel<GroupQueryVO> addPowerToGroup(@RequestBody @Validated AddPow2GroupDTO addPow2GroupDTO) {

		ResultModel<GroupQueryVO> msgReturn = new ResultModel<GroupQueryVO>();
		GroupQueryVO groupQueryVO = groupService.addPowerToGroup(addPow2GroupDTO);
		log.info(JSONObject.toJSONString(groupQueryVO));
		msgReturn.setData(groupQueryVO);
		return msgReturn;
	}

	@ApiOperation(value = "删除整个权限分组", httpMethod = "POST")
	@PostMapping(value = "/deletePowerGroup")
	public ResultModel<GroupQueryVO> deletePowerGroup(@RequestBody @Validated AddPow2GroupDTO  deleteGroupDTO) {

		ResultModel<GroupQueryVO> msgReturn = new ResultModel<GroupQueryVO>();
		GroupQueryVO groupQueryVO = groupService.deletePowerGroup(deleteGroupDTO);
		log.info(JSONObject.toJSONString(groupQueryVO));
		msgReturn.setData(groupQueryVO);
		return msgReturn;
	}
}
