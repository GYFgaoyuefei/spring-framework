package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PowerQueryDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String powerName;
    
    private String keywords;
    
    private Integer status;
    
    private int page=0;
    
    private int size=10;
}
