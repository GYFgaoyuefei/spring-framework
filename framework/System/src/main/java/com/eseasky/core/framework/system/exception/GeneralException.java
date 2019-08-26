package com.eseasky.core.framework.system.exception;

import lombok.Getter;
import lombok.Setter;

public class GeneralException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Setter
	@Getter
	private String code;
	
	@Setter
	@Getter
	private String msg;
	
	public GeneralException(String code, String msg){
		super(msg);
		this.setCode(code);
		this.setMsg(msg);
	}
	
//	private String getExceptionMsg(String code, String msg) {
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append(code).append(" ： ").append(msg);
//		return stringBuilder.toString();
//	}
	public GeneralException(String[] msg){
		super(msg[1]);
		this.setCode(msg[0]);
		this.setMsg(msg[1]);
	}
	
	public GeneralException(String[] msg, String extInfo){
		super(msg[1] + ", 详情：" + extInfo);
		this.setCode(msg[0]);
		this.setMsg(msg[1] + ", 详情：" + extInfo);
	}
	
	public String[] getExceptionInfo() {
		return new String[] {this.getCode(), this.getMsg()};
	}
}
