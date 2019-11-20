package com.eseasky.core.framework.SecurityManage.protocol.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	
	private String name;
	
	private Long roleId;
	
	private String roleName;
	
	private List<Long> role_id;
	
	private int page = 0;
	
	private int pageSize = 10;

}
