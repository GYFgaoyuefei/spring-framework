package com.eseasky.protocol.auth.entity.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GrantByGroupDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 171578317197462703L;

	@ApiModelProperty(value = "授权人员名称")
	@NotEmpty(message = "授权人员名称不能为空")
	private String account;

	@ApiModelProperty(value = "权限分组名称")
	@NotEmpty(message = "权限分组名称不能为空")
	private String groupName;

	@ApiModelProperty(value = "创建人员")
	@NotEmpty(message = "创建人员不能为空")
	private String createAccount;

	@ApiModelProperty(value = "组织")
	@NotEmpty(message = "组织不能为空")
	private String orgCode;

	public GrantByGroupDTO() {

	}

	public GrantByGroupDTO(@NotEmpty(message = "授权人员名称不能为空") String account,
			@NotEmpty(message = "权限分组名称不能为空") String groupName, @NotEmpty(message = "创建人员不能为空") String createAccount,
			@NotEmpty(message = "组织不能为空") String orgCode) {
		super();
		this.account = account;
		this.groupName = groupName;
		this.createAccount = createAccount;
		this.orgCode = orgCode;
	}
}
