package com.eseasky.core.framework.AuthService.controller;


import com.eseasky.core.framework.AuthService.utils.QueryMulOrgsUtils;
import com.eseasky.global.entity.MsgPageInfo;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgRefreshDTO;
import com.eseasky.protocol.auth.protocol.OrgRefreshServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.MulOrgsVO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgServiceFeign;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(value = "组织管理", tags = "组织管理服务")
@RestController
@Log4j2
public class OrgFeignController implements OrgServiceFeign {

    @Autowired
    private OrgService orgService;
    
    @Autowired
    OrgRefreshServiceFeign orgRefreshServiceFeign;

    @Override
    @ApiOperation(value = "查询组织", httpMethod = "POST")
    public ResultModel<List<OrgQueryVO>> queryOrg(@RequestBody OrgQueryDTO orgQueryDTO) {
        ResultModel<List<OrgQueryVO>> resultModel = new ResultModel<>();
        Page<OrgQueryVO> orgQueryVOS = orgService.queryOrg(orgQueryDTO);
        if (orgQueryVOS != null) {
            MsgPageInfo msgPageInfo = new MsgPageInfo();
            msgPageInfo.setSize(orgQueryVOS.getPageable().getPageSize());
            msgPageInfo.setTotal(orgQueryVOS.getTotalElements());
            msgPageInfo.setPage(orgQueryVOS.getPageable().getPageNumber());
            resultModel.setData(orgQueryVOS.getContent(), msgPageInfo);
        }
        return resultModel;
    }


    @Override
    @ApiOperation(value = "添加组织", httpMethod = "POST")
    public OrgSaveVO saveOrg(@RequestBody @Validated OrgSaveDTO orgSaveDTO) {

        OrgSaveVO orgSaveVO = orgService.saveForApp(orgSaveDTO);
        log.info(JSONObject.toJSONString(orgSaveVO));
        return orgSaveVO;
    }

    @Override
    @ApiOperation(value = "更新组织", httpMethod = "POST")
    public OrgSaveVO updateOrg(@RequestBody @Validated OrgUpByCodeDTO OrgUpdateDTO) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = orgService.updateOrgByCode(OrgUpdateDTO);
        log.info(JSONObject.toJSONString(orgSaveVO));
        return orgSaveVO;
    }

    @Override
    @ApiOperation(value = "禁用组织", httpMethod = "POST")
    public OrgSaveVO disableOrg(@RequestBody @Validated OrgQueryDTO orgUpdateDTO) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = orgService.disableOrg(orgUpdateDTO);
        log.info(JSONObject.toJSONString(orgSaveVO));
        return orgSaveVO;
    }

    @Override
    @ApiOperation(value = "获取组织名称", httpMethod = "POST")
	public OrgSaveVO getOrgNameByOrgCode(@RequestBody OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		OrgSaveVO orgSaveVO = orgService.getOrgNameByOrgCode(orgQueryDTO.getOrgCode());
		log.info(JSONObject.toJSONString(orgSaveVO));
		return orgSaveVO;
	}

	@Override
	@ApiOperation(value = "商家入驻归属组织", httpMethod = "POST")
	public ResultModel<List<MulOrgsVO>> queryOrgsByMerCode(@RequestBody List<OrgQueryDTO> orgQueryDTOList) {
		// TODO Auto-generated method stub
		ResultModel<List<MulOrgsVO>> resultModel = new ResultModel<>();
        Set<String> orgCodes = QueryMulOrgsUtils.getOrgCodes(orgQueryDTOList.stream().map(OrgQueryDTO::getOrgCode).collect(Collectors.toList()));
        List<MulOrgsVO> orgSaveVO = orgService.queryOrgsByMerCode(orgCodes);
		log.info(JSONObject.toJSONString(orgSaveVO));
		if(orgSaveVO!=null) {
			resultModel.setData(orgSaveVO);
		}
		return resultModel;
	}

    @Override
    @ApiOperation(value = "添加组织", httpMethod = "POST")
    public ResultModel<List<OrgQueryVO>> queryAndSaveOrg(@RequestBody List<OrgQueryDTO> orgQueryDTOList) {
        ResultModel<List<OrgQueryVO>> result = new ResultModel<>();
        List<OrgQueryVO> orgQuerys = orgService.queryAndSaveOrg(orgQueryDTOList);
        result.setData(orgQuerys);
        return result;
    }


    @ApiOperation(value = "刷新组织", httpMethod = "POST")
    public ResultModel<OrgSaveVO> refresh(@RequestBody OrgRefreshDTO orgRefreshDTO) {
        return orgRefreshServiceFeign.orgRefresh(orgRefreshDTO);
    }
}
