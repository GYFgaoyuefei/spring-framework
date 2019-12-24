package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AddPow2GroupDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @NotEmpty(message = "分组名称不能为空")
	private String groupName;
	
	private  List<Long> powerIds;
}
