package com.eseasky.core.framework.system.models.entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name="sys_dict_item", indexes= {
		@Index(name="uni_index_dict_item_status", columnList="status"),
		@Index(name="uni_index_dict_item_key", columnList="`key`")
})
@ApiModel(value="DictItem",description="字典项表定义")
@Data
public class DictItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8050660443876463036L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(value="字典项id")
	private Long id;
	
	@Column(name="`key`")
	@ApiModelProperty(value="字典项key")
	private String key;
	
	@Column(name="value", length=4096)
	@ApiModelProperty(value="字典项value")
	private String value;

	@ApiModelProperty(value="字典项名称")
	private String name;

	@ApiModelProperty(value="字典项状态")
	private String status;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dict_id")
	@ApiModelProperty(value="关联到字典表")
	private Dictionary dictionary;

}
