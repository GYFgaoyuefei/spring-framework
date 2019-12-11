package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class GroupQueryVO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private long id;
    
    private String groupName;
    
    private String note;
    
    private int powerSize;
    
    private int status;
}
