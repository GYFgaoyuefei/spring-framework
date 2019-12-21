package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.Set;

import com.eseasky.core.starters.organization.persistence.model.OrgPowerItem;

import lombok.Data;

@Data
public class PowerSaveVO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String groupName;
	
	private String authCode = "000";

	private int status;
	
	private String note;
	
	private Set<OrgPowerItem> items;
}
