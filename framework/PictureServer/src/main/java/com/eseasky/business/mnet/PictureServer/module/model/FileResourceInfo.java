package com.eseasky.business.mnet.PictureServer.module.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="PictureResource", description="图片资源信息表")
@Entity
@Table(name="file_resource_info", indexes= {
		@Index(name="file_resource_info_file_md5", columnList="fileMd5", unique=true),
		@Index(name="file_resource_info_file_id", columnList="fileId", unique=true)

})
@EntityListeners(AuditingEntityListener.class)
@Data
public class FileResourceInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value="文件ID")
	private String fileId;
	
	@ApiModelProperty(value="文件名字")
	private String fileName;
	
	@ApiModelProperty(value="文件md5值")
	private String fileMd5;
	
	@ApiModelProperty(value="资源类型")
	private String resourceType;
	
	@ApiModelProperty(value="资源路径")
	private String resourcePath;
	
	private String published = "N";
	
	@ApiModelProperty(value="组织编码")
	private String organization;
	
	@ApiModelProperty(value = "创建时间(第一次入库)")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private Date createTime;
	
	@ApiModelProperty(value = "操作时间(变更时间)")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private Date operTime;

}
