package com.eseasky.core.framework.AuthService.module.service;



import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.ServUserInfoVO;
import com.eseasky.core.framework.AuthService.protocol.vo.UserGrantInfoVO;
import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;

@Service
public interface ServUserInfoService {
	ServUserInfo findByUserName(String userName);
	
	ServUserInfo findByPhone(String phone);
	
	ServUserInfo loginByCode(String phone, String code);
	
	void smsLoginCodeSend(String phone);
	
	ServUserInfoVO addUserInfo(ServUserInfoDTO servUserInfoDTO);

	Page<ServUserInfo> queryUserInfo(ServUserInfoDTO servUserInfoDTO);

    ServUserInfoVO findById(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO updateServUserInfo(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO deleteServUserInfoById(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO updatePwd(ServUserInfoDTO servUserInfoDTO);
	
	boolean CheckUsername(ServUserInfoDTO servUserInfoDTO);
	
	ServUserInfoVO forceOffLine(ServUserInfoDTO servUserInfoDTO);

	UserGrantInfoVO getUserGranted(String account);
}
