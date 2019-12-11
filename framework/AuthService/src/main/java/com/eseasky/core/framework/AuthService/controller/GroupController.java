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
import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupSaveDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupQueryVO;
import com.eseasky.core.framework.AuthService.protocol.vo.GroupSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
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
	@PostMapping(value = "/saveGroup")
	public ResultModel<GroupSaveVO> saveGroup(@RequestBody  @Validated GroupSaveDTO groupSaveDTO) {

		ResultModel<GroupSaveVO> msgReturn = new ResultModel<GroupSaveVO>();
		GroupSaveVO groupSaveVO = groupService.saveGroup(groupSaveDTO);
		log.info(JSONObject.toJSONString(groupSaveVO));
		msgReturn.setData(groupSaveVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "更新权限分组", httpMethod = "POST")
	@PostMapping(value = "/updateGroup")
	public ResultModel<GroupSaveVO> updateGroup(@RequestBody  @Validated GroupUpdateDTO groupUpdateDTO) {

		ResultModel<GroupSaveVO> msgReturn = new ResultModel<GroupSaveVO>();
		GroupSaveVO groupSaveVO = groupService.updateGroup(groupUpdateDTO);
		log.info(JSONObject.toJSONString(groupSaveVO));
		msgReturn.setData(groupSaveVO);
		return msgReturn;
	}
	
	@ApiOperation(value = "更新权限分组", httpMethod = "POST")
	@PostMapping(value = "/deleteGroup")
	public void deleteGroup(@RequestBody GroupUpdateDTO groupUpdateDTO) {
		groupService.deleteGroup(groupUpdateDTO);
	}
	
	@ApiOperation(value = "查询权限分组", httpMethod = "POST")
    @PostMapping(value = "/queryGroup")
    public ResultModel<List<GroupQueryVO>> queryGroup(@RequestBody GroupQueryDTO groupQueryDTO) {

        ResultModel<List<GroupQueryVO>> msgReturn = new ResultModel<List<GroupQueryVO>>();
        Page<GroupQueryVO> groupQueryVOs = groupService.queryGroup(groupQueryDTO);
        log.info(JSONObject.toJSONString(groupQueryVOs));
        msgReturn.setData(groupQueryVOs.getContent(),MsgPageInfo.loadFromPageable(groupQueryVOs));
        return msgReturn;
    }
	
	@ApiOperation(value = "授权", httpMethod = "POST")
	@PostMapping(value = "/grant")
	public ResultModel<OrgGrantInfoVO> grant(@RequestBody @Validated GroupGrantDTO groupGrantDTO) {

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

}
