package com.eseasky.core.framework.SecurityManage.protocol.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class MenuTreeVO {
    
    private Long id;
    private String group;// 资源分类
    private String name;
    private String note;
    private String type;// 资源类型
    private String status;// 资源状态valid 有效，invalid无效
    
    private Long menuId;

    private String menuKey;
    private String menuLink;
    private String icon;
    private int level;
    private Long parentId;
    private int order;
	
    private Long apiId;
    private Long elementId;
    private String elementKey;
    private String elementLink;
    private String style;
    
    private String key;
    private String title;
    private String parentName;
	private Long powerId;
	
	@JsonInclude(Include.NON_NULL)
	private List<MenuTreeVO> children;
}
