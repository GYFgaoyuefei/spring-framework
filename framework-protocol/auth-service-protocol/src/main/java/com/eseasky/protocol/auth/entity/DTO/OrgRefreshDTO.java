package com.eseasky.protocol.auth.entity.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author admin_z by 2019/12/20
 * @ClassName OrgSaveDTO
 */
@Data
public class OrgRefreshDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer level;

    private String orgCode;

    @NotEmpty(
            message = "组织名称不能为空"
    )
    private String name;

    @NotEmpty(
            message = "组织描述不能为空"
    )
    private String note;

    private String parentOrgCode;

    private int status;
}
