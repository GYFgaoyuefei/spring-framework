package com.eseasky.protocol.auth.protocol.hystrix;

import com.eseasky.core.starters.feign.wrapper.fallbacks.IHystrix;

import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.GrantByGroupDTO;
import com.eseasky.protocol.auth.entity.DTO.ServUserInfoDTO;
import com.eseasky.protocol.auth.entity.VO.ServUserInfoVO;
import com.eseasky.protocol.auth.entity.VO.UserGrantInfoVO;
import com.eseasky.protocol.auth.protocol.GrantAndUserFeign;

import java.util.List;

public class GrantAndUserFeignHystrix implements GrantAndUserFeign , IHystrix {

	private Throwable throwable;

	@Override
	public Throwable setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return null;
	}

	@Override
	public ResultModel<UserGrantInfoVO> grantByGroup(GrantByGroupDTO grantByGroupDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
	}

	@Override
	public ResultModel<List<ServUserInfoVO>> queryUserInfo(ServUserInfoDTO servUserInfoDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
	}

	@Override
	public ResultModel<UserGrantInfoVO> rejectByGroup(GrantByGroupDTO groupRejectDTO) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
	}

	@Override
	public ResultModel<List<UserGrantInfoVO>> grantsByGroup(List<GrantByGroupDTO> needGrant) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
	}

	@Override
	public ResultModel<List<UserGrantInfoVO>> rejectsByGroup(List<GrantByGroupDTO> needReject) {
		// TODO Auto-generated method stub
		throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
	}



}
