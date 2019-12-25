package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrgGrantInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @NotEmpty(message = "user不能为空")
    private String user;
    
    @NotEmpty(message = "orgCode不能为空")
    private String orgCode;
    
    private String orgName;
    
    private String resourceName;
    
    private Set<String> action;
    
    @NotNull(message = "resId不能为空")
    private long resId;
    
    @NotEmpty(message = "createUser不能为空")
    private String createUser;
    
    @NotEmpty(message = "grantedType不能为空")
    private String grantedType="reject";
    
    private Long grantId;
    
}
