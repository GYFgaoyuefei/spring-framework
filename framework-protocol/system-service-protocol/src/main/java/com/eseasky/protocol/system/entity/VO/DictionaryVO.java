package com.eseasky.protocol.system.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DictionaryVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="字典id")
	private Long id;
	
	@ApiModelProperty(value="字典类型")
	private String type;
	
	@ApiModelProperty(value="字典名称")
	private String name;

	@ApiModelProperty(value="字典状态")
	private String status;

	@ApiModelProperty(value="是否可编辑")
	private String editable;

	@ApiModelProperty(value="描述")
	private String desc;
	
	@ApiModelProperty(value="字典分组")
	private String group;
	
	@ApiModelProperty(value="字典项定义")
	private List<DictItemVO> dictItems;
	
	@ApiModelProperty(value="错误信息")
	private String message;
}
