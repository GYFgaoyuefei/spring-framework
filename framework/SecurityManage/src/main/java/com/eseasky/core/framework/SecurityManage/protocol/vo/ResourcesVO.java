package com.eseasky.core.framework.SecurityManage.protocol.vo;

import lombok.Data;

@Data
public class ResourcesVO {
	private Long id;
	
	private String group;// 资源分类：
	
	private String name;
	
	private String note;// 资源描述
	
	private String type;// 资源类型
	
	private String status;// 资源状态valid 有效，invalid无效
	
	private ApiVO api;
	
	private ElementVO element;
	
	private MenuVO menu;
	
//	private String uri;// url地址
//
//	private String method;// 
//	
//	private String description;// 资源描述
//	
//	private Long parent_id;
//	
//	private Integer order;
	
}
