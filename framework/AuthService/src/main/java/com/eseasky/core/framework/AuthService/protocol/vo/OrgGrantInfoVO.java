package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.List;

import com.eseasky.core.starters.organization.persistence.entity.OrgGrantedItemForUserGroup;

import lombok.Data;

@Data
public class OrgGrantInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String user;
    
    private List<OrgGrantedItemForUserGroup> granteds;
}
