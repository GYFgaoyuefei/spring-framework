package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

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
    
    @NotNull(message = "action不能为空")
    private int action;
    
    @NotNull(message = "resId不能为空")
    private long resId;
    
    private String createUser;
}
