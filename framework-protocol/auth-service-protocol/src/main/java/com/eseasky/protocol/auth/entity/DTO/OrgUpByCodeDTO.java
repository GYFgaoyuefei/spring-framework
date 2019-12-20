package com.eseasky.protocol.auth.entity.DTO;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

@Data
public class OrgUpByCodeDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @NotEmpty(
            message = "组织编码不能为空"
        )
    private String orgCode;

    @NotEmpty(
            message = "组织名称不能为空"
        )
    private String name;


}
