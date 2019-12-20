package com.eseasky.core.framework.AuthService.protocol.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import com.eseasky.core.starters.organization.persistence.entity.OrgUserGranted;

@EqualsAndHashCode(callSuper = false)
@Data
public class ServUserInfoVO implements Serializable {
    private static final long serialVersionUID = -5073050740838461415L;
    @ApiModelProperty(value = "用户ID")
    private Long id;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String passWord;
    @ApiModelProperty(value = "用户联系方式")
    private String mobile;
    @ApiModelProperty(value = "用户状态 0-下线 1-上线")
    private String state;
    @ApiModelProperty(value = "组织编码")
    private String orgCode;
    @ApiModelProperty(value = "组织编码")
    private String orgName;
    @ApiModelProperty(value = "用户角色")
    private OrgUserGranted roles;
}
