package com.eseasky.protocol.auth.entity;

import java.io.Serializable;

import com.eseasky.global.entity.UserViews;

import lombok.Data;

@Data
public class TokenVO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String access_token;
	
	private String token_type;
	
	private UserViews userView;

}