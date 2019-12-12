package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrgQueryDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer level;
    
    private String parentCode;
    
    @NotNull(
            message = "组织id不能为空"
        )
    private Long id;
    
    private String orgCode;
    
    private int status = 1;
    
    private String keyWords;
    
    private int page = 0;
    
    private int size = 50;
}
