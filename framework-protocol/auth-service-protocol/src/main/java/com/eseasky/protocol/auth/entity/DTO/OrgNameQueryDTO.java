package com.eseasky.protocol.auth.entity.DTO;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class OrgNameQueryDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Set<String> orgCodes;

}
