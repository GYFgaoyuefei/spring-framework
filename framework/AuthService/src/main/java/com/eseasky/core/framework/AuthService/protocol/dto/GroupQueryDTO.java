package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GroupQueryDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String groupName;
    
    private String keywords;
    
    private Integer status;
    
    private int page;
    
    private int size;
}
