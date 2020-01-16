package com.eseasky.core.framework.AuthService.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.service.OrgRefreshService;
import com.eseasky.core.starters.feign.wrapper.FeignBuilder;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgRefreshDTO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgRefreshServiceFeign;

@Service
public class OrgRefreshServiceImpl implements OrgRefreshService {

	@Autowired
	private OrgRefreshServiceFeign orgRefreshServiceFeign;
	
//	@Async("asyncServiceExecutor")
//	@Override
//	public ResultModel<OrgSaveVO> orgRefresh(OrgRefreshDTO orgRefreshDTO) {
//		// TODO Auto-generated method stub
//		if(orgRefreshDTO!=null) {
//			return orgRefreshServiceFeign.orgRefresh(orgRefreshDTO);
//		}
//		return null;
//	}

	@Async("asyncServiceExecutor")
	@Override
	public ResultModel<OrgSaveVO> orgRefresh(OrgRefreshDTO orgRefreshDTO, String authorization) {
		// TODO Auto-generated method stub
		if(orgRefreshDTO!=null) {
			OrgRefreshServiceFeign orgFeign = FeignBuilder.clientBuilder(OrgRefreshServiceFeign.class).addStaticHeaders("authorization", authorization).toDefaultClient();
			return orgFeign.orgRefresh(orgRefreshDTO);
		}
		return null;
	}

}
