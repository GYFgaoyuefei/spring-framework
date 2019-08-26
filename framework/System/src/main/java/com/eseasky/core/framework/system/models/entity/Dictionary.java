package com.eseasky.core.framework.system.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name="sys_dictionary", indexes= {
		@Index(name="sys_dictionary_uni_index_type_group", columnList="type,group", unique=true),
		@Index(name="sys_dictionary_index_STATUS", columnList="status")
})
@ApiModel(value="Dictionary",description="字典表定义")
@Data
public class Dictionary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -711111343823177305L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	@Column(name="`desc`")
	@ApiModelProperty(value="描述")
	private String desc;
	
	@Column(name="`group`")
	@ApiModelProperty(value="字典分组")
	private String group;
	
	@OneToMany(mappedBy = "dictionary", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value="dictionary")
	@ApiModelProperty(value="字典项定义")
	private List<DictItem> dictItems;
}
