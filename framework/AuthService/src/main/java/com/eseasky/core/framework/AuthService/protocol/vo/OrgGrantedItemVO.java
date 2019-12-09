package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrgGrantedItemVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long resId;
    
    private String user;
    
    private String resGroup;
    
    private String resNote;
    
    private int action;
    
    private String orgCode;
}
