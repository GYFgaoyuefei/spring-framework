package com.eseasky.core.framework.SecurityManage.protocol.vo;

import java.util.Set;

import lombok.Data;
@Data
public class RoleVO {
	private Long id;
	
	private String name;
	
	private int region;
	
	private String status;
	
	private String desc;
	
	private Set<PowerVO> powers;
}
