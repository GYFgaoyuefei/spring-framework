package com.eseasky.protocol.auth.protocol.hystrix;

import org.springframework.data.domain.Page;

import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgServiceFeign;

public class OrgServiceFeignHystrix implements OrgServiceFeign {

	@Override
	public Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, "error");
	}

	@Override
	public OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, "error");
	}

	

}
