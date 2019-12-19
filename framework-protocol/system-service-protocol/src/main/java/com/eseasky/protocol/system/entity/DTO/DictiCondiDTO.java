package com.eseasky.protocol.system.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DictiCondiDTO {
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
