package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class GrantInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String user;
    
    private String orgCode;
    
    private Set<String> action;
    
    private long resId;
    
    private String resourceName;
    
    private String createUser;
    
    private Long grantId;
    
    private String orgName;
    
    private String message;
    
}
