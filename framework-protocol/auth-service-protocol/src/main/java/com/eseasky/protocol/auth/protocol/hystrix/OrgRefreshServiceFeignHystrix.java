package com.eseasky.protocol.auth.protocol.hystrix;

import com.eseasky.core.starters.feign.wrapper.fallbacks.IHystrix;
import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgRefreshDTO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.eseasky.protocol.auth.protocol.OrgRefreshServiceFeign;

public class OrgRefreshServiceFeignHystrix implements OrgRefreshServiceFeign, IHystrix {


	private Throwable throwable;

	@Override
	public Throwable setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return null;
	}

	@Override
	public ResultModel<OrgSaveVO> orgRefresh(OrgRefreshDTO orgRefreshDTO) {
		throw new BaseHandlerException(500, throwable == null ? "未知异常" : throwable.getMessage());
	}
}
