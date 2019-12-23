package com.eseasky.protocol.system.entity.DTO;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictiCondiDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 1 AuthContextHelper.currentUser() 判断用户是否存在
     * 2 如果为null则校验此字段校验方法为依赖com.eseasky.core.starters security-tools 模块使用TokenUtils.check(NO_LOGIN_KEY, data)
     * 3 SystemServiceConfig中定义了NO_LOGIN_KEY
     */
    private String authKey;//  2 如果为null则校验此字段校验方法为依赖com.eseasky.core.starters security-tools 模块使用TokenUtils.check(NO_LOGIN_KEY, data)
    
    @ApiModelProperty(value="字典id列表")
    private List<Long> ids; // 字典id列表
    @ApiModelProperty(value="字典名称")
    private String name;
    @ApiModelProperty(value="字典分组")
    private String group;
    @ApiModelProperty(value="字典状态")
    private String status;
    @ApiModelProperty(value="字典类型")
    private String type;
    @ApiModelProperty(value="字典项key")
    private String itemKey;
    @ApiModelProperty(value="字典项名称")
    private String itemName;
    @ApiModelProperty(value="字典项状态")
    private String itemStatus;
    @ApiModelProperty(value="分页页码")
    private int page = 0;
    @ApiModelProperty(value="每页条数")
    private int pageSize = 1000;
}
