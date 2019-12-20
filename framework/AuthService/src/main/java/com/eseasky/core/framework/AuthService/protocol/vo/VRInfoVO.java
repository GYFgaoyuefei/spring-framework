package com.eseasky.core.framework.AuthService.protocol.vo;

import lombok.Data;

@Data
public class VRInfoVO {
	
    private Long id;
    
    private String name;
    
    private Integer level;
    
    private String note;
    
    private String parentOrgCode;
    
    private String orgCode;
    
    private int status;

}
