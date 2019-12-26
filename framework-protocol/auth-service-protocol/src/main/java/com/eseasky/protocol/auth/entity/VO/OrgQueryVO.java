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
    
    private String level1OrgCode;
    
    private String level2OrgCode;
    
    private String parentOrgCode;
    
    private String parentOrgName;
    
    private String orgCode;
    
    private int status;

    //多个组织编码申请 专用 判断该组织编码是否被重复申请
    private String message;

}
