package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ServUserInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String passWord;
	private String mobile;
	private String state;
	private String[] orgNameForSave;
	private String orgCode;
	private String orgName;
	private Integer page = 0;
	private Integer size = 10;

}
