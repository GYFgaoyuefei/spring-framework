package com.eseasky.core.framework.AuthService.security;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eseasky.core.framework.AuthService.module.model.ServUserInfo;
import com.eseasky.core.framework.AuthService.module.service.ServUserInfoService;
import com.eseasky.core.starters.auth.server.core.entity.FrontEndAuthConfig;
import com.eseasky.core.starters.auth.server.core.interfaces.UserService;
import com.eseasky.global.entity.UserViews;
import com.eseasky.protocol.system.entity.DTO.DictiCondiDTO;
import com.eseasky.protocol.system.protocol.SystemServiceFeign;


/**
 * 用户信息服务
 * 实现 Spring Security的UserDetailsService接口方法，用于身份认证
 */
@Service
public class DomainUserDetailsService implements UserService {
	private static final String FRONTEND_DICT_GROUP = "AUTH_FRONTEND";
	private static final String FRONTEND_DICT_TYPE = "System";
    @Autowired
    private ServUserInfoService	servUserInfoService;    // 账户数据操作接口
    
    @Autowired
    private SystemServiceFeign systemServiceFeign;
    
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


	
	@Override
	public List<FrontEndAuthConfig> getFrontConfig() {
		// TODO Auto-generated method stub
		DictiCondiDTO dto = new DictiCondiDTO();
		dto.setGroup(FRONTEND_DICT_GROUP);
		dto.setType(FRONTEND_DICT_TYPE);
		dto.setItemKey("MERCHANT_MENU");
		dto.setStatus("valid");
//		ResponseEntity<MsgReturn<DictItemVO>> configs = systemServiceFeign.queryByKeyAndDictId(dto);
		List<FrontEndAuthConfig> send = new ArrayList<FrontEndAuthConfig>();
//		if (configs != null && configs.getBody() != null) {
//			MsgReturn<DictItemVO> msg = configs.getBody();
//			FrontEndAuthConfig configItem = new FrontEndAuthConfig();
//			configItem.setKey(msg.getData().getKey());
//			configItem.setRelatePower(msg.getData().getValue().split(","));
//			send.add(configItem);
//		}
		FrontEndAuthConfig configItem = new FrontEndAuthConfig();
		configItem.setKey("MERCHANT_MENU");
		configItem.setRelatePower("商家查询,商家新增".split(","));
		send.add(configItem);
		return send.isEmpty() ? null : send;
	}
}
