package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrgUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(
        message = "组织id不能为空"
    )
    private Long id;
    
    private String name;
    
    private String note;
    
    private Integer status;
}
