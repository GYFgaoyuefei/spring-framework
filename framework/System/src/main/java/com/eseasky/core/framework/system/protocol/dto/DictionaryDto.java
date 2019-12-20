package com.eseasky.core.framework.system.protocol.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictionaryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="上传文件")
	private List<MultipartFile> files;
	
	@ApiModelProperty(value="字典id")
	private Long id;
	
	@ApiModelProperty(value="字典类型")
	private String type;
	
	@ApiModelProperty(value="字典名称")
	private String name;

	@ApiModelProperty(value="字典状态")
	private String status;

	@ApiModelProperty(value="是否可编辑")
	private String editable = "Y";

	@ApiModelProperty(value="描述")
	private String desc;
	
	@ApiModelProperty(value="字典分组")
	private String group;
	
	@ApiModelProperty(value="字典项定义")
	private List<DictItemDto> dictItems;
	
	@ApiModelProperty(value="字典项id列表")
	private List<Long> dictItemIds;
}
