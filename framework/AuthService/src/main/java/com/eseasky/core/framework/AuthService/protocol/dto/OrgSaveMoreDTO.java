package com.eseasky.core.framework.AuthService.protocol.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;

import javax.validation.Valid;

@Data
public class OrgSaveMoreDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
   
    @Valid
    private Set<OrgSaveDTO> orgSavMore;
}
