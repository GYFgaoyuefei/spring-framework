package com.eseasky.core.framework.SecurityManage.protocol.vo;

import java.util.List;

import lombok.Data;

@Data
public class GroupApisVO {
	
	private String title;
	
	private String key;
	
	private List<ApiVO> children;

}
