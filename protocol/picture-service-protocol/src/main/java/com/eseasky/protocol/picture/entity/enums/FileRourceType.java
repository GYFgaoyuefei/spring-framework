package com.eseasky.protocol.picture.entity.enums;

import lombok.Getter;
import lombok.Setter;

public enum FileRourceType {
	PICTURE("picture"),VIDEO("video");
	
	@Setter
	@Getter
	private String value;
	
	FileRourceType(String value) {
		setValue(value);
	}
}
