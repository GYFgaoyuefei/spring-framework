package com.eseasky.core.framework.AuthService.exception.BusiException;

public enum  BusiEnum {



    SUCCESS(200,"SUCCESS"),
   
	NOT_FOUND_USER(500, "系统提示：未找到用户信息"),
	
	ARGS_NOT_ISEXIST(500, "系统提示：接收参数为空"),
	
	USERINFO_NOID(500, "系统提示：用户id为空"),
	
	USERNAME_REPEATABLE(500,"系统提示：用户名重复"),
	
	MOBILE_REPEATABLE(500,"系统提示：手机号重复"),	
	
	ORGNAME_REPEATABLE(500,"系统提示：组织名重复"),
	
	NO_ORGNAME(500,"系统提示：组织名称不能为空"),
	
	NO_ORGOFCODE(500,"系统提示：不存在该组织编码的组织"),
	
	USERINFO_IDNOTNULL(500,"系统提示：id不能为null"),

    USER_INVALID(500,"用户失效"),
    
    USERINFO_ORGIDNOTNULL(500,"组织不能未null"),
    
    USERINFO_GROUPGRANT(500,"无法为该用户授权该权限"),

    NOTDELETE(500,"管理员不可删除"),

    GET_ORGCODE_ERROR(500,"组织编码生成失败"),
	
	GROUP_NOID(500, "系统提示：授权分组id为空");

    private Integer code;

    private String message;

    //private String url;
    BusiEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
