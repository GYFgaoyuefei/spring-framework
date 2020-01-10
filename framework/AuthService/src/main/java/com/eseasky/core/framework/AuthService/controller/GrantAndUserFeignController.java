package com.eseasky.core.framework.AuthService.controller;


import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.protocol.GrantAndUserFeign;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.module.service.GroupService;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.module.service.ServUserInfoService;
import com.eseasky.core.framework.AuthService.protocol.dto.GroupGrantDTO;
import com.eseasky.protocol.auth.entity.DTO.GrantByGroupDTO;
import com.eseasky.protocol.auth.entity.DTO.ServUserInfoDTO;
import com.eseasky.protocol.auth.entity.VO.ServUserInfoVO;
import com.eseasky.protocol.auth.entity.VO.UserGrantInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "授权和用户管理", tags = "授权和用户管理服务")
@RestController
@Log4j2
public class GrantAndUserFeignController implements GrantAndUserFeign{

    @Autowired
    private GroupService groupService;
    
    @Autowired
    private ServUserInfoService servUserInfoService;
    
	@Autowired
	private OrgService orgService;
    
	@ApiOperation(value = "权限分组授权", httpMethod = "POST")
	@Override
	@RequestMapping(value="/grantByGroup",method= RequestMethod.POST,consumes = "application/json")
	public ResultModel<UserGrantInfoVO> grantByGroup(@RequestBody @Validated GrantByGroupDTO grantByGroupDTO) {
		// TODO Auto-generated method stub
		ResultModel<UserGrantInfoVO> result=new ResultModel<UserGrantInfoVO>();
		UserGrantInfoVO userGrantInfoVO=null;
		if(grantByGroupDTO!=null) {
			GroupGrantDTO groupGrantDTO=new GroupGrantDTO();
			BeanUtils.copyProperties(grantByGroupDTO, groupGrantDTO);
			userGrantInfoVO=groupService.grantByGroup(groupGrantDTO);			
		}	
		log.info(JSONObject.toJSONString(userGrantInfoVO));
		if(userGrantInfoVO!=null) {
			result.setData(userGrantInfoVO);
		} 
		return result;
	}

	@ApiOperation(value = "查询用户", httpMethod = "POST")
	@Override
	@RequestMapping(value="/queryUserInfo",method= RequestMethod.POST,consumes = "application/json")
	public ResultModel<List<ServUserInfoVO>> queryUserInfo(@RequestBody ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		ResultModel<List<ServUserInfoVO>> msgReturn = new ResultModel<List<ServUserInfoVO>>();
        Page<ServUserInfo> page = servUserInfoService.queryUserInfo(servUserInfoDTO);
        List<ServUserInfoVO> list = page.stream().map(item -> {
            ServUserInfoVO servUserInfoVO = new ServUserInfoVO();
            BeanUtils.copyProperties(item, servUserInfoVO,"passWord");
            servUserInfoVO.setOrgName(orgService.getOrgNameByOrgCode(item.getOrgCode()).getName());
            return servUserInfoVO;
        }).collect(Collectors.toList());
        log.info(JSONObject.toJSONString(list));
        msgReturn.setData(list, MsgPageInfo.loadFromPageable(page));
        return msgReturn;
	}

	@Override
	@RequestMapping(value="/rejectByGroup",method= RequestMethod.POST,consumes = "application/json")
	public ResultModel<UserGrantInfoVO> rejectByGroup(@RequestBody @Validated GrantByGroupDTO groupRejectDTO) {
		// TODO Auto-generated method stub
		ResultModel<UserGrantInfoVO> msgReturn = new ResultModel<UserGrantInfoVO>();
		UserGrantInfoVO userGrantInfoVO=null;
		if(groupRejectDTO!=null) {
			GroupGrantDTO groupGrantDTO=new GroupGrantDTO();
			BeanUtils.copyProperties(groupRejectDTO, groupGrantDTO);
			userGrantInfoVO=groupService.rejectByGroup(groupGrantDTO);			
		}	
		log.info(JSONObject.toJSONString(userGrantInfoVO));
		msgReturn.setData(userGrantInfoVO);
		return msgReturn;
	}

	@Override
	@RequestMapping(value="/grantsByGroup",method= RequestMethod.POST,consumes = "application/json")
	public ResultModel<List<UserGrantInfoVO>> grantsByGroup(@RequestBody List<GrantByGroupDTO> needGrant) {
		// TODO Auto-generated method stub
		ResultModel<List<UserGrantInfoVO>> results=new ResultModel<List<UserGrantInfoVO>>();
		if(needGrant!=null) {
			List<UserGrantInfoVO> UserGrantInfoVOs=new ArrayList<UserGrantInfoVO>();
			for(GrantByGroupDTO grantByGroupDTO:needGrant) {
				UserGrantInfoVO userGrantInfoVO=null;
				if(grantByGroupDTO!=null) {
					GroupGrantDTO groupGrantDTO=new GroupGrantDTO();
					BeanUtils.copyProperties(grantByGroupDTO, groupGrantDTO);
					userGrantInfoVO=groupService.grantByGroup(groupGrantDTO);			
				}	
				if(userGrantInfoVO!=null) {
					UserGrantInfoVOs.add(userGrantInfoVO);
				} 
			}
			results.setData(UserGrantInfoVOs);
		}
		return results;
	}

	@Override
	@RequestMapping(value="/rejectsByGroup",method= RequestMethod.POST,consumes = "application/json")
	public ResultModel<List<UserGrantInfoVO>> rejectsByGroup(@RequestBody List<GrantByGroupDTO> needReject) {
		// TODO Auto-generated method stub
		ResultModel<List<UserGrantInfoVO>> results=new ResultModel<List<UserGrantInfoVO>>();
		if(needReject!=null) {
			List<UserGrantInfoVO> UserGrantInfoVOs=new ArrayList<UserGrantInfoVO>();
			for(GrantByGroupDTO grantByGroupDTO:needReject) {
				UserGrantInfoVO userGrantInfoVO=null;
				if(grantByGroupDTO!=null) {
					GroupGrantDTO groupGrantDTO=new GroupGrantDTO();
					BeanUtils.copyProperties(grantByGroupDTO, groupGrantDTO);
					userGrantInfoVO=groupService.rejectByGroup(groupGrantDTO);			
				}	
				if(userGrantInfoVO!=null) {
					UserGrantInfoVOs.add(userGrantInfoVO);
				} 
			}
			results.setData(UserGrantInfoVOs);
		}
		return results;
	}


   
}
