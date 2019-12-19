package com.eseasky.protocol.picture.entity.VO;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PictureInfoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="文件ID")
	private String fileId;
	
	@ApiModelProperty(value="文件名称")
	private String fileName;
	
	@ApiModelProperty(value="资源类型")
	private String resourceType;
	
	@ApiModelProperty(value="资源路径")
	private String resourcePath;
	
	@ApiModelProperty(value="组织编码")
	private String organization;
	
	@ApiModelProperty(value = "创建时间(第一次入库)")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	
	@ApiModelProperty(value = "操作时间(变更时间)")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;
}
