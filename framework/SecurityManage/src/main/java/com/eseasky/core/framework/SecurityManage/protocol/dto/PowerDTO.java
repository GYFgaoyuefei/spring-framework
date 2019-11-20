package com.eseasky.core.framework.SecurityManage.protocol.dto;

import lombok.Data;

@Data
public class PowerDTO {
	
	private Long id;//权限id
	private String operation;// 权限操作
	
	private String status;// 权限和资源状态valid 有效，invalid无效
	
	private String group;// 资源分类：
	private String name;// 资源名称
	private String note;// 资源描述
	private String type;// 资源类型
	
	private String url;// api的url地址
	private String serverName;// api的服务名称
	
	private String key;// 菜单和元素的英文唯一标识
	private String link;// 菜单和元素的跳转链接
	
	private Long apiId;// 元素关联的apiId
	private String style;// 元素样式
	private Long menuId;// 元素所在的菜单id
	
	private int level;// 菜单层级
	private String icon;// 菜单样式
	private Long parentId;// 菜单上级id
	private Integer order;// 菜单排序
	
	private Long res_id;//资源id
	private int page = 0;
	private int pageSize = 10000;
}
