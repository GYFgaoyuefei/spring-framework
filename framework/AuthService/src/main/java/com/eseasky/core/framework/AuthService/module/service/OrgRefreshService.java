package com.eseasky.core.framework.AuthService.module.service;


import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.auth.entity.DTO.OrgRefreshDTO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;

public interface OrgRefreshService {
	public ResultModel<OrgSaveVO> orgRefresh(OrgRefreshDTO orgRefreshDTO, String authorization);
}
