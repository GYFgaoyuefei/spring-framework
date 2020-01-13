package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ServUserInfoUpdateDTO implements Serializable {

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
	@NotEmpty(
	        message = "手机号不能为空"
	    )
	private String mobile;
	private String state;
	private List<Long> powerIds;
	private List<String> groupNames;
	@NotEmpty(
	        message = "组织不能为空"
	    )
	private String orgCode;
//	private String orgName;
	@NotEmpty(
	        message = "授权操作用户不能为空"
	    )
	private String createrUser;
	
	private String nickName;
	
	private List<String> loginAvailable;

}
