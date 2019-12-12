package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PowerGroupCreateItemDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @NotNull(
        message = "资源id不能为空"
    )
    private Long resId;
    
//    @NotNull(
//        message = "权限操作不能为空"
//    )
    private Set<String> action;
}
