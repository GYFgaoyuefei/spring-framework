package com.eseasky.core.framework.PictureServer.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eseasky.core.framework.PictureServer.module.model.PictureResource;

@Service
public interface PictureResourceService {

	public PictureResource uploadSingle(String resourceType,  MultipartFile file, String organization);

	public PictureResource getPictureResourceByFilename(String fileName, HttpServletResponse response);

}
