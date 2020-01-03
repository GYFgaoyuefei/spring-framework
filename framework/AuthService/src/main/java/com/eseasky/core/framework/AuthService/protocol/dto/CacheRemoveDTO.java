package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CacheRemoveDTO implements Serializable {
    private static final long serialVersionUID = 1L;
   
//    @NotEmpty(
//        message = "cacheName不能为空"
//    )
    private String cacheName;
    
//    @NotEmpty(
//        message = "权限分组名称不能为空"
//    )
    private String key;
    
    private Boolean isMatchStart=false;
    
}
