package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class GroupGrantDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @NotEmpty(message = "用户不能为空")
	private String account;
	
	@NotEmpty(message = "操作员不能为空")
	private String createAccount;
	
	@NotEmpty(message = "分组不能为空")
	private String groupName;
	
	@NotEmpty(message = "组织不能为空")
	private String orgCode;
}
