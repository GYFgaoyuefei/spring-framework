package com.eseasky.protocol.picture.entity.DTO;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.eseasky.protocol.picture.entity.enums.FileRourceType;

import lombok.Data;

@Data
public class PictureInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private MultipartFile file;
	private String resourceType = FileRourceType.PICTURE.getValue();
	private String organization;
}
