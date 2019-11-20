package com.eseasky.core.framework.SecurityManage.protocol.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoleDTO {
	private Long id;
	
	private String name;
	
	private String desc;
	
	private String status;
	
	private String type;
	
	private Long userId;
	
	private List<Long> res_id;
	
	private int page = 0;
	
	private int pageSize = 10;
}
