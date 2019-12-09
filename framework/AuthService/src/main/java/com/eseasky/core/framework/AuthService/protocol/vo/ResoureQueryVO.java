package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.List;

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
    
    private int action;
    
    private List<String> actionArr;
}
