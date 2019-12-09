package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrgQueryDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer level;
    
    private String parentCode;
    
    private Long id;
    
    private String orgCode;
    
    private int status = 1;
    
    private String keyWords;
    
    private int page = 0;
    
    private int size = 10000;
}
