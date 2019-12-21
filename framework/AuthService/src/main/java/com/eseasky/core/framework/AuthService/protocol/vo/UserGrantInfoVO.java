package com.eseasky.core.framework.AuthService.protocol.vo;


import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserGrantInfoVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String user;
	
	private List<OrgGraItemForUserGroupVO> extraGranteds;
	
	private List<VRInfoVO> granteds;

}
