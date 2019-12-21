package com.eseasky.core.framework.AuthService.protocol.vo;

import java.io.Serializable;
import java.util.List;

import com.eseasky.core.starters.organization.persistence.entity.vo.PowerVO;

import lombok.Data;

@Data
public class GroupSaveVO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private Long id;
		
	private String name;
		
	private String note;
	
	private List<PowerVO> power;
}
