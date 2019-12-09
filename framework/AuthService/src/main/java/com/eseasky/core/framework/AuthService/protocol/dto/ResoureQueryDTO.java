package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResoureQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int page = 0;
    
    private int pageSize = 10;
    
    private Long resId;
    
    private String group;
    
    private String keywords;
    
    private Integer status;
}
