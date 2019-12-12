package com.eseasky.core.framework.AuthService.protocol.dto;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

@Data
public class OrgSaveDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer level;
    
    private String parentOrgCode;

    @NotEmpty(
            message = "组织名称不能为空"
        )
    private String name;
    
    @NotEmpty(
            message = "组织描述不能为空"
        )
    private String note;

}
