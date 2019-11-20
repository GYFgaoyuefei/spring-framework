package com.eseasky.core.framework.SecurityManage.protocol.vo;

import java.util.List;

import lombok.Data;
@Data
public class PowerTreeVO {
	
	RoleVO roleVO;
	
	List<MenuTreeVO> MenuTreeVOs;
	
	List<String> key;
	
	List<String> apiKey;
	
	List<Long> ids;
	
	List<PowerVO> apiVOs;
	
	List<String> group;
	
	List<Long> apiId;
	
	List<GroupApisVO> groupApisVOs;
	
}
