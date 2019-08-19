package com.eseasky.core.framework.AuthService.module.service;



import org.springframework.stereotype.Service;
import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;





@Service
public interface ServUserInfoService {
	ServUserInfo findByUserName(String userName);
}
