package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GroupGrantDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @NotEmpty(
        message = "授权用户不能为空"
    )
    private String user;
    
    @NotEmpty(
        message = "组织不能为空"
    )
    private String orgCode;
    
    @NotNull(
        message = "分组信息不能为空"
    )
    private Long groupId;
    
    @NotNull(
        message = "授权操作者不能为空"
    )
    private String createUser;
}
