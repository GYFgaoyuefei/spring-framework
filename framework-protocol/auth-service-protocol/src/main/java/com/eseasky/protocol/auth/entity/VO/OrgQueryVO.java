package com.eseasky.protocol.auth.entity.VO;

import lombok.Data;

@Data
public class OrgQueryVO {
	
    private Long id;
    
    private String name;
    
    private Integer level;
    
    private String note;
    
    private String parentOrgCode;
    
    private String orgCode;
    
    private int status;

}
