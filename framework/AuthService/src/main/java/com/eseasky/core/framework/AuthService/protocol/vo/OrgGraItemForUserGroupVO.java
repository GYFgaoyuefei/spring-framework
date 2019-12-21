package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
@Data
public class OrgGraItemForUserGroupVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private int action;
    
    private List<String> actionArr;
    
    private String orgCode;
    
    private String orgName;
    
    private long resId;
	
	private String resGroup;
	
	private String resName;
	
	private String resNote;
	
	private String resType;
	
	private String type;
    
    private String user;
    
    private String grantType;
    
    private Timestamp createTime;
}
