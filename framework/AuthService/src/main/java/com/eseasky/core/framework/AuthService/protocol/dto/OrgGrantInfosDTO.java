package com.eseasky.core.framework.AuthService.protocol.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class OrgGrantInfosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Valid
    List<OrgGrantInfoDTO> orgGrantInfoDTOs;
}
