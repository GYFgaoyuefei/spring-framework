package com.eseasky.protocol.auth.entity.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrgQueryDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer level;
    
    private String parentCode;
    
    @NotNull(
            message = "组织id不能为空"
        )
    //这个非空校验是为了在禁用组织接口复用这个dto
    private Long id;
    
    private String orgCode;
    
    private int status = 1;
    
    private String keyWords;

    private String note;
    
    private int page = 0;
    
    private int size = 50;
}
