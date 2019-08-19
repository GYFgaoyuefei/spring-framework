package com.eseasky.core.framework.PictureServer.module.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="PictureResource", description="图片资源信息表")
@Entity
@Table(name="picture_resource", indexes= {
		@Index(name="picture_resource_file_md5", columnList="fileMd5", unique=true)
})
@Data
public class PictureResource implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value="文件名称")
	private String fileName;
	
	@ApiModelProperty(value="文件md5值")
	private String fileMd5;
	
	@ApiModelProperty(value="资源类型")
	private String resourceType;
	
	@ApiModelProperty(value="资源路径")
	private String resourcePath;
	
	@ApiModelProperty(value="组织编码")
	private String organization;

}
