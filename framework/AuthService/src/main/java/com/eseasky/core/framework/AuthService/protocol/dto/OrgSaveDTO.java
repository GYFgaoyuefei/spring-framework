package com.eseasky.core.framework.AuthService.protocol.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrgSaveDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer level;
    
    private String parentOrgCode;

    private String name;
    
    private String note;

}
