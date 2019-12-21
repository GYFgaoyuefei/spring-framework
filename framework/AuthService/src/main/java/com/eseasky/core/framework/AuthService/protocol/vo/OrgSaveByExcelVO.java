package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class OrgSaveByExcelVO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int successNum;
	
	private int errorNum;
	
	private int count;
	
	private List<OrgRowSaveVO> orgRowSaveVOs;

}
