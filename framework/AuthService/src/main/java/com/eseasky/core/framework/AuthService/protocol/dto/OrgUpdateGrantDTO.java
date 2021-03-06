package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrgUpdateGrantDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @NotNull(message = "id不能为空")
    private Long id;
    
    private Integer action;
    
    @NotEmpty(message = "组织不能为空")
    private String orgCode;
    
    @NotEmpty(message = "grantType不能为空")
    private String grantType="ACCEPT";
    
}
