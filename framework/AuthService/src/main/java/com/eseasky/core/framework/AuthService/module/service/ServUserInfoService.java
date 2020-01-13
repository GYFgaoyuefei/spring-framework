package com.eseasky.core.framework.AuthService.module.service;



import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.protocol.dto.ServUserInfoUpdateDTO;
import com.eseasky.protocol.auth.entity.DTO.ServUserInfoDTO;
import com.eseasky.protocol.auth.entity.VO.ServUserInfoVO;
import com.eseasky.protocol.auth.entity.VO.UserGrantInfoVO;

@Service
public interface ServUserInfoService {
	ServUserInfo findByUserName(String userName);
	
	ServUserInfo findByPhone(String phone);
	
	ServUserInfo loginByCode(String phone, String code);
	
	void smsLoginCodeSend(String phone);
	
	ServUserInfoVO addUserInfo(ServUserInfoDTO servUserInfoDTO);

	Page<ServUserInfo> queryUserInfo(ServUserInfoDTO servUserInfoDTO);

    ServUserInfoVO findById(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO updateServUserInfo(ServUserInfoUpdateDTO servUserInfoDTO);

	ServUserInfoVO deleteServUserInfoById(ServUserInfoDTO servUserInfoDTO);

	ServUserInfoVO updatePwd(ServUserInfoDTO servUserInfoDTO);
	
	boolean CheckUsername(ServUserInfoDTO servUserInfoDTO);
	
	ServUserInfoVO forceOffLine(ServUserInfoDTO servUserInfoDTO);

	UserGrantInfoVO getUserGranted(String account);
}
