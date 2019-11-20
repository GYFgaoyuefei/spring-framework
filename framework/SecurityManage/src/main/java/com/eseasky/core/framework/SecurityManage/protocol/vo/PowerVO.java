package com.eseasky.core.framework.SecurityManage.protocol.vo;

import lombok.Data;

@Data
public class PowerVO {
	private Long id;

	private String name;

	private String operation;

	private String status;
	
	private String parent_name;

	private ResourcesVO resources;
}
