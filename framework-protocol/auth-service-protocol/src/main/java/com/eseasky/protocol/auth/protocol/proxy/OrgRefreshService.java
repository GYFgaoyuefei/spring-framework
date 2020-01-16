package com.eseasky.protocol.auth.protocol.proxy;


import com.eseasky.global.entity.ResultModel;

import com.eseasky.protocol.auth.entity.DTO.OrgRefreshDTO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgRefreshServiceFeign;


public class OrgRefreshService implements OrgRefreshServiceFeign {
	
	private OrgRefreshServiceFeign feign;
	
	public OrgRefreshService(OrgRefreshServiceFeign feign) {
		this.feign = feign;
	}

	@Override
	public ResultModel<OrgSaveVO> orgRefresh(OrgRefreshDTO orgRefreshDTO) {
		// TODO Auto-generated method stub
		return feign.orgRefresh(orgRefreshDTO);
	}
	
	

}
