package com.eseasky.core.framework.AuthService.module.service;



import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.ServUserInfoVO;

@Service
public interface ServUserInfoService {
	ServUserInfo findByUserName(String userName);
	
	ServUserInfoVO addUserInfo(ServUserInfoDTO servUserInfoDTO);
	
	Page<ServUserInfo> queryUserInfo(ServUserInfoDTO servUserInfoDTO);

    ServUserInfoVO findById(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO updateServUserInfo(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO deleteServUserInfoById(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO updatePwd(ServUserInfoDTO servUserInfoDTO);
	
	boolean CheckUsername(ServUserInfoDTO servUserInfoDTO);
	
	ServUserInfoVO forceOffLine(ServUserInfoDTO servUserInfoDTO);
}
