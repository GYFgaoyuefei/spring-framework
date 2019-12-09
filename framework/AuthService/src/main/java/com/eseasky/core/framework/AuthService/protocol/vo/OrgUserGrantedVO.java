package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrgUserGrantedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String user;

    private int action;

    private String orgCode;
 
    private int level;
    
    private String createUser;
    
    private Timestamp createTime;
  
    private ResoureQueryVO resource;
}
