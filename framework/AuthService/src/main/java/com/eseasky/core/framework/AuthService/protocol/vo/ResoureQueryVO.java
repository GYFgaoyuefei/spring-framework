package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;

import lombok.Data;
@Data
public class ResoureQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String resourceType;
    
    private String resourceName;
    
    private String resourceGroup;
    
    private String note;
    
    private int status;
    
//    private String user;

    private int action;
    
//    private String orgCode;
//    
//    private Integer leavel;
}