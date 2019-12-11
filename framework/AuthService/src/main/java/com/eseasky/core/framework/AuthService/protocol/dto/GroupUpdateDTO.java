package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GroupUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @NotNull(
        message = "权限分组id不能为空"
    )
    private Long groupId;
    
    private String groupName;
    
    private String note;
    
    @Valid
    @NotEmpty(
            message = "items不能为空"
        )
    private List<PowerGroupCreateItemDTO> items;
}
