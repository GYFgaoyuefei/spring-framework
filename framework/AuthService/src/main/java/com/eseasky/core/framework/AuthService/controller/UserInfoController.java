package com.eseasky.core.framework.AuthService.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.module.service.ServUserInfoService;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.VRInfoDTO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.ServUserInfoDTO;
import com.eseasky.protocol.auth.entity.VO.ServUserInfoVO;
import com.eseasky.protocol.auth.entity.VO.UserGrantInfoVO;
import com.google.common.base.Strings;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(value = "用户管理", tags = "用户管理服务")
@RestController
@Log4j2
@RequestMapping("/UserInfoManage")
public class UserInfoController {
    @Autowired
    private ServUserInfoService servUserInfoService;
    
	@Autowired
	private OrgService orgService;

    @ApiOperation(value = "新建用户", httpMethod = "POST")
    @PostMapping(value = "/add")
    public ResultModel<ServUserInfoVO> addUserInfo(@RequestBody @Validated ServUserInfoDTO servUserInfoDTO) {

        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<ServUserInfoVO>();
        ServUserInfoVO servUserInfoVO = servUserInfoService.addUserInfo(servUserInfoDTO);
        log.info(JSONObject.toJSONString(servUserInfoVO));
        msgReturn.setData(servUserInfoVO);
        return msgReturn;
    }

    @ApiOperation(value = "查询用户", httpMethod = "POST")
    @PostMapping(value = "/query")
    public ResultModel<List<ServUserInfoVO>> queryUserInfo(@RequestBody ServUserInfoDTO servUserInfoDTO) {

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
    
    @ApiOperation(value = "修改用户", httpMethod = "POST")
    @PostMapping(value = "/updateUserInfo", consumes = "application/json")
    public ResultModel<ServUserInfoVO> updateUserInfo(@RequestBody @Validated ServUserInfoUpdateDTO servUserInfoDTO) {
        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<>();
        ServUserInfoVO servUserInfoVO = servUserInfoService.updateServUserInfo(servUserInfoDTO);
        msgReturn.setData(servUserInfoVO);
        return msgReturn;
    }

    @ApiOperation(value = "根据id删除用户", httpMethod = "POST")
    @PostMapping(value = "/delUserInfoById", consumes = "application/json")
    public ResultModel<ServUserInfoVO> delUserInfoById(@RequestBody ServUserInfoDTO servUserInfoDTO) {
        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<>();
        ServUserInfoVO servUserInfoVO = servUserInfoService.deleteServUserInfoById(servUserInfoDTO);
        msgReturn.setData(servUserInfoVO);
        return msgReturn;
    }

    @ApiOperation(value = "更换密码", httpMethod = "POST")
    @PostMapping(value = "/changepwd")
    public ResultModel<ServUserInfoVO> changePwd(@RequestBody ServUserInfoDTO servUserInfoDTO) {
        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<>();
        ServUserInfoVO userInfoVO = servUserInfoService.updatePwd(servUserInfoDTO);
        msgReturn.setData(userInfoVO);
        return msgReturn;
    }

    @ApiOperation(value = "查看用户", httpMethod = "POST")
    @PostMapping(value = "/getuserinfo")
    public ResultModel<ServUserInfoVO> getUserInfo(@RequestBody ServUserInfoDTO servUserInfoDTO) {
        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<>();
        ServUserInfoVO servUserInfoVO = servUserInfoService.findById(servUserInfoDTO);
        msgReturn.setData(servUserInfoVO);
        return msgReturn;
    }
    
    @ApiOperation(value = "查看用户", httpMethod = "POST")
    @PostMapping(value = "/getUserInfoByUserName")
    public ResultModel<ServUserInfoVO> getUserInfoByUserName(@RequestBody ServUserInfoDTO servUserInfoDTO) {
        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<>();
        ServUserInfoVO servUserInfoVO=null;
        ServUserInfo servUserInfo  = servUserInfoService.findByUserName(servUserInfoDTO.getUserName());
        if(servUserInfo!=null) {
        	servUserInfoVO=new ServUserInfoVO();
        	BeanUtils.copyProperties(servUserInfo, servUserInfoVO,"passWord");
        }        	
        msgReturn.setData(servUserInfoVO);
        return msgReturn;
    }

    @ApiOperation(value = "强制下线", httpMethod = "POST")
    @PostMapping(value = "/forceOffLine")
    public ResultModel<ServUserInfoVO> forceOffLine(@RequestBody ServUserInfoDTO servUserInfoDTO) {
        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<>();
        ServUserInfoVO servUserInfoVO = servUserInfoService.forceOffLine(servUserInfoDTO);
        msgReturn.setData(servUserInfoVO);
        return msgReturn;
    }
    
    @ApiOperation(value = "退出登录", httpMethod = "POST")
    @PostMapping(value = "/loginOut")
    public ResultModel<ServUserInfoVO> loginOut(@RequestBody ServUserInfoDTO servUserInfoDTO) {
        ResultModel<ServUserInfoVO> msgReturn = new ResultModel<>();
        ServUserInfoVO servUserInfoVO = servUserInfoService.forceOffLine(servUserInfoDTO);
        msgReturn.setData(servUserInfoVO);
        return msgReturn;
    }


    @ApiOperation(value = "用户授权信息", httpMethod = "POST")
    @PostMapping(value = "/getUserGranted")
    public ResultModel<UserGrantInfoVO> getUserGranted(@RequestBody VRInfoDTO vRInfoDTO) {
        ResultModel<UserGrantInfoVO> msgReturn = new ResultModel<UserGrantInfoVO>();
        UserGrantInfoVO userGrantInfoVO=null;
        if(vRInfoDTO!=null && !Strings.isNullOrEmpty(vRInfoDTO.getAccount()))
        	userGrantInfoVO = servUserInfoService.getUserGranted(vRInfoDTO.getAccount());
        msgReturn.setData(userGrantInfoVO);
        log.info(System.currentTimeMillis());
        return msgReturn;
    }
}
