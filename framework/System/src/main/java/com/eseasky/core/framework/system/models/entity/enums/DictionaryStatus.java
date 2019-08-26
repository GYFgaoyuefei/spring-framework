package com.eseasky.core.framework.system.models.entity.enums;

public enum DictionaryStatus {
	VALID("valid"), INVALID("invalid");
	
	
	private String value;
	private DictionaryStatus(String value) {
		setValue(value);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
