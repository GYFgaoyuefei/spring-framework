package com.eseasky.core.framework.AuthService.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.module.service.ServUserInfoService;
import com.eseasky.core.starters.auth.server.core.interfaces.UserService;
import com.eseasky.global.entity.UserViews;


/**
 * 用户信息服务
 * 实现 Spring Security的UserDetailsService接口方法，用于身份认证
 */
@Service
public class DomainUserDetailsService implements UserService {

    @Autowired
    private ServUserInfoService	servUserInfoService;    // 账户数据操作接口
    
	@Override
	public UserViews getUserByName(String name) {
		// TODO Auto-generated method stub
		if (name != null) {
			ServUserInfo account = servUserInfoService.findByUserName(name);
			if (account != null) {
				UserViews userViews = new UserViews();
				userViews.setId(account.getId());
				userViews.setName(name);
				userViews.setPassword(account.getPassWord());
				return userViews;
			}
		}
		return null;
	}

	@Override
	public UserViews getUserBySms(String phone, String code) {
		// TODO Auto-generated method stub
		ServUserInfo account = servUserInfoService.loginByCode(phone, code);
		if (account != null) {
			UserViews userViews = new UserViews();
			userViews.setId(account.getId());
			userViews.setName(account.getUserName());
			return userViews;
		}
		return null;
	}
}
