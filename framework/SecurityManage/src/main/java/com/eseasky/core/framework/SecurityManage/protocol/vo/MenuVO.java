package com.eseasky.core.framework.SecurityManage.protocol.vo;

import lombok.Data;

@Data
public class MenuVO {

	private Long id;
	
	private String key;
	
	private int level;
	
	private Long parentId;
	
	private String link;
	
	private int order;
	
	private String icon;
}
