package com.eseasky.core.framework.SecurityManage.protocol.vo;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class UserVO {
	
	private Long id;
	
	private String name;
	
	private String mobile;
	
	private String mail;
	
	private List<String> role_name;
	
	private List<String> key;
	
	private Set<RoleVO> roleVOs;
	
}
