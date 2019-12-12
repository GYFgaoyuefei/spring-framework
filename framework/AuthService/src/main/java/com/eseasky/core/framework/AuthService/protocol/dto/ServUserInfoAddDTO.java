package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ServUserInfoAddDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String userName;
	
	private String passWord;
	
	private String mobile;
	
	private String state;
	
	private String[] orgCode;
	
	private String nikeName;

}
