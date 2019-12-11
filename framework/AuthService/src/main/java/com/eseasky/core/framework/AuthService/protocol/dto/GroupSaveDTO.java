package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.eseasky.core.starters.organization.persistence.entity.PowerGroupCreateItem;

import lombok.Data;

@Data
public class GroupSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotEmpty(
        message = "权限分组名称不能为空"
    )
    private String groupName;
    @NotEmpty(
        message = "权限分组描述不能为空"
    )
    private String note;
    
    @Valid
    private List<PowerGroupCreateItem> items;
}
