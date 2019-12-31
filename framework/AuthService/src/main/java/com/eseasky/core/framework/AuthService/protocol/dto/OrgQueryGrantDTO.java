package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrgQueryGrantDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private String user;
    
    private int action = 0;
    
    private String group;
    
    private Long resId;
    
    private String orgCode;
    
    private String keyWords;
    
    private String type;
    
    private int size = 10;
    
    private int page = 0;
}
