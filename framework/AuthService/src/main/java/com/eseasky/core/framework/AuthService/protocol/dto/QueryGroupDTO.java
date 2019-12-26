package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class QueryGroupDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @NotNull(
            message = "id不能为空"
        )
    private Long id;
	
	private String name;
	
	private int page=0;
	
	private int size=10;
}
