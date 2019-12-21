package com.eseasky.core.framework.AuthService.protocol.dto;

import lombok.Data;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

@Data
public class OrgSaveMoreDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer level;
    
    private String parentOrgCode;
    
    private MultipartFile file;
}
