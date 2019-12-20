package com.eseasky.protocol.auth.protocol.hystrix;

import com.eseasky.core.starters.feign.wrapper.fallbacks.IHystrix;
import org.springframework.data.domain.Page;

import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgServiceFeign;

import java.util.List;

public class OrgServiceFeignHystrix implements OrgServiceFeign , IHystrix {

	@Override
	public ResultModel<List<OrgQueryVO>> queryOrg(OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, "error");
	}

	@Override
	public OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, "error");
	}

	@Override
	public OrgSaveVO updateOrg(OrgUpByCodeDTO OrgUpdateDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, "error");
	}

	@Override
	public OrgSaveVO disableOrg(OrgQueryDTO orgUpdateDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, "error");
	}

	@Override
	public OrgSaveVO getOrgNameByOrgCode(OrgQueryDTO orgQueryDTO) {
		// TODO Auto-generated method stub
		return null;
	}

private Throwable throwable;
	@Override
	public Throwable setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return null;
	}
}
