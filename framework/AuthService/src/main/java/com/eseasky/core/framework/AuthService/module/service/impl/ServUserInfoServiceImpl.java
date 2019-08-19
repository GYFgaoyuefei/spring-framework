package com.eseasky.core.framework.AuthService.module.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.module.repository.ServUserInfoRepository;
import com.eseasky.core.framework.AuthService.module.service.ServUserInfoService;


@Service
public class ServUserInfoServiceImpl implements ServUserInfoService {
	
	@Autowired
	ServUserInfoRepository servUserInfoRepository;
	
	@Override
	public ServUserInfo findByUserName(String userName) {
		// TODO Auto-generated method stub
		Optional<ServUserInfo> op = servUserInfoRepository.findByUserName(userName);
		  if(op.isPresent()){
			  ServUserInfo userInfo = op.get();
			  return userInfo;
          }
		return null;
	}

}
