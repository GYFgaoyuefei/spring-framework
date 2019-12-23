package com.eseasky.protocol.auth.entity.VO;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrgSaveVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
    
    private String name;
    
    private Integer level;
    
    private String note;
    
    private String parentOrgCode;
    
    private String orgCode;
    
    private int status;

}
