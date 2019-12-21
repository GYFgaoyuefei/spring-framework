package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class GroupSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotEmpty(
        message = "权限分组名称不能为空"
    )
    private String name;
    @NotEmpty(
        message = "权限分组描述不能为空"
    )
    private String note;
    
	private Long[] ids;
}
