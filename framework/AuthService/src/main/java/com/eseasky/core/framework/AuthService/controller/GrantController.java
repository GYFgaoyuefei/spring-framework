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
import com.eseasky.core.framework.AuthService.module.service.GrantService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgGrantInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgQueryGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateGrantDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.ResoureQueryDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.GrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgGrantedItemVO;
import com.eseasky.core.framework.AuthService.protocol.vo.ResoureQueryVO;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(value = "角色管理", tags = "角色管理服务")
@RestController
@Log4j2
@RequestMapping("/GrantManage")
public class GrantController {

	@Autowired
	private GrantService roleService;
	
	@ApiOperation(value = "查询授权资源", httpMethod = "POST")
    @PostMapping(value = "/queryResoureItem")
    public ResultModel<List<ResoureQueryVO>> queryResoureItem(@RequestBody ResoureQueryDTO resoureQueryDTO) {

        ResultModel<List<ResoureQueryVO>> msgReturn = new ResultModel<List<ResoureQueryVO>>();
        Page<ResoureQueryVO> resoureQueryVOs = roleService.queryResoureItem(resoureQueryDTO);
        log.info(JSONObject.toJSONString(resoureQueryVOs));
        if(resoureQueryVOs!=null)
        msgReturn.setData(resoureQueryVOs.getContent(),MsgPageInfo.loadFromPageable(resoureQueryVOs));
        return msgReturn;
    }
	
	@ApiOperation(value = "授权", httpMethod = "POST")
    @PostMapping(value = "/grant")
    public ResultModel<GrantInfoVO> grant(@RequestBody @Validated OrgGrantInfoDTO orgGrantInfoDTO) {

        ResultModel<GrantInfoVO> msgReturn = new ResultModel<GrantInfoVO>();
        GrantInfoVO orgGrantInfoVO = roleService.grant(orgGrantInfoDTO);
        log.info(JSONObject.toJSONString(orgGrantInfoVO));
        msgReturn.setData(orgGrantInfoVO);
        return msgReturn;
    }
	
//	@ApiOperation(value = "授权", httpMethod = "POST")
//    @PostMapping(value = "/grantMore")
//    public ResultModel<List<GrantInfoVO>> grant(@Valid @RequestBody OrgGrantInfosDTO orgGrantInfoDTOs) {
//        ResultModel<List<GrantInfoVO>> msgReturn = new ResultModel<List<GrantInfoVO>>();
//        List<GrantInfoVO> orgGrantInfoVOs = roleService.grant(orgGrantInfoDTOs);
//        log.info(JSONObject.toJSONString(orgGrantInfoVOs));
//        msgReturn.setData(orgGrantInfoVOs);
//        return msgReturn;
//    }
	
	@ApiOperation(value = "更新授权", httpMethod = "POST")
    @PostMapping(value = "/updateGrant")
    public ResultModel<OrgGrantInfoVO> updateGrant(@RequestBody @Validated OrgUpdateGrantDTO orgUpdateGrantDTO) {

        ResultModel<OrgGrantInfoVO> msgReturn = new ResultModel<OrgGrantInfoVO>();
        OrgGrantInfoVO orgGrantInfoVO = roleService.updateGrant(orgUpdateGrantDTO);
        log.info(JSONObject.toJSONString(orgGrantInfoVO));
        msgReturn.setData(orgGrantInfoVO);
        return msgReturn;
    }
	
	@ApiOperation(value = "删除授权", httpMethod = "POST")
    @PostMapping(value = "/deleteGrant")
    public ResultModel<OrgGrantInfoVO> deleteGrant(@RequestBody OrgUpdateGrantDTO orgUpdateGrantDTO) {

        ResultModel<OrgGrantInfoVO> msgReturn = new ResultModel<OrgGrantInfoVO>();
        OrgGrantInfoVO orgGrantInfoVO = roleService.deleteGrant(orgUpdateGrantDTO);
        log.info(JSONObject.toJSONString(orgGrantInfoVO));
        msgReturn.setData(orgGrantInfoVO);
        return msgReturn;
    }
	
	@ApiOperation(value = "查询已授权", httpMethod = "POST")
    @PostMapping(value = "/queryGrant")
    public ResultModel<List<OrgGrantedItemVO>> queryGranted(@RequestBody OrgQueryGrantDTO orgQueryGrantDTO) {

        ResultModel<List<OrgGrantedItemVO>> msgReturn = new ResultModel<List<OrgGrantedItemVO>>();
        Page<OrgGrantedItemVO> orgGrantedItemVOs = roleService.queryGranted(orgQueryGrantDTO);
        log.info(JSONObject.toJSONString(orgGrantedItemVOs));
        if(orgGrantedItemVOs!=null)
        msgReturn.setData(orgGrantedItemVOs.getContent(),MsgPageInfo.loadFromPageable(orgGrantedItemVOs));
        return msgReturn;
    }
	
//	@ApiOperation(value = "查询授权", httpMethod = "POST")
//    @PostMapping(value = "/queryOrgUserGrant")
//    public ResultModel<List<OrgUserGrantedVO>> queryOrgUserGranted(@RequestBody OrgQueryGrantDTO orgQueryGrantDTO) {
//
//        ResultModel<List<OrgUserGrantedVO>> msgReturn = new ResultModel<List<OrgUserGrantedVO>>();
//        Page<OrgUserGrantedVO> orgGrantedItemVOs = roleService.queryOrgUserGranted(orgQueryGrantDTO);
//        log.info(JSONObject.toJSONString(orgGrantedItemVOs));
//        if(orgGrantedItemVOs!=null)
//        msgReturn.setData(orgGrantedItemVOs.getContent(),MsgPageInfo.loadFromPageable(orgGrantedItemVOs));
//        return msgReturn;
//    }
	
	@ApiOperation(value = "查询授权", httpMethod = "POST")
    @PostMapping(value = "/queryOrgUserGrant")
    public ResultModel<List<ResoureQueryVO>> queryOrgUserGranted(@RequestBody OrgQueryGrantDTO orgQueryGrantDTO) {

        ResultModel<List<ResoureQueryVO>> msgReturn = new ResultModel<List<ResoureQueryVO>>();
        Page<ResoureQueryVO> orgGrantedItemVOs = roleService.queryOrgUserGranted(orgQueryGrantDTO);
        log.info(JSONObject.toJSONString(orgGrantedItemVOs));
        if(orgGrantedItemVOs!=null)
        msgReturn.setData(orgGrantedItemVOs.getContent(),MsgPageInfo.loadFromPageable(orgGrantedItemVOs));
        return msgReturn;
    }
}
