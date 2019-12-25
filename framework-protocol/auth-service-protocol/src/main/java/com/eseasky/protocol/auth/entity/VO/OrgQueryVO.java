package com.eseasky.protocol.auth.entity.VO;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrgQueryVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
    
    private String name;
    
    private Integer level;
    
    private String note;
    
    private String parentOrgCode;
    
    private String parentOrgName;
    
    private String orgCode;
    
    private int status;

}
