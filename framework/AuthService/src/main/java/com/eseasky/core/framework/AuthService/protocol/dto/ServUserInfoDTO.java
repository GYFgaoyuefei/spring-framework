package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ServUserInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotEmpty(
	        message = "用户名不能为空"
	    )
	private String userName;
	@NotEmpty(
	        message = "密码不能为空"
	    )
	private String passWord;
	private String mobile;
	private String state;
	private String[] orgNameForSave;
	private List<Integer> groupIds;
	@NotEmpty(
	        message = "组织不能为空"
	    )
	private String orgCode;
	private String orgName;
	@NotEmpty(
	        message = "授权操作用户不能为空"
	    )
	private String createrUser;
	private Integer page = 0;
	private Integer size = 10;

}
