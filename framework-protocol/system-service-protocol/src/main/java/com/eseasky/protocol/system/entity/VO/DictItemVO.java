package com.eseasky.protocol.system.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictItemVO implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="字典项id")
	private Long id;
	
	@ApiModelProperty(value="字典项key")
	private String key;
	
	@ApiModelProperty(value="字典项value")
	private String value;

	@ApiModelProperty(value="字典项名称")
	private String name;

	@ApiModelProperty(value="字典项状态")
	private String status;
}
