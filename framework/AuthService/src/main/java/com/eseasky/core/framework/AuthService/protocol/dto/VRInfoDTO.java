package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class VRInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id; 
    
    private String account;
    
}
