package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MulOrgsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrgQueryVO levelFirOrg;
	
	private List<OrgQueryVO> levelSecOrgs;
}
