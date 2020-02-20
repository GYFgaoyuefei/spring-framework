package com.eseasky.business.mnet.PictureServer.service;

import javax.servlet.http.HttpServletResponse;

import com.eseasky.protocol.picture.entity.VO.NewFileResourceInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eseasky.business.mnet.PictureServer.module.model.FileResourceInfo;

@Service
public interface PictureResourceService {

	public FileResourceInfo uploadSingle(String resourceType,  MultipartFile file, String organization, String published,String width,String height);

	public void getPictureResourceByFilename(String fileName, HttpServletResponse response);

	public void getPictureResourceByFileId(String fileId, HttpServletResponse response);
	

	public void noLoginAccessByFileId(String fileId, HttpServletResponse response);
	
	public NewFileResourceInfo getMultipartFileByFileId(String fileId);

	public FileResourceInfo uploadByfileId(String fileId, String resourceType, MultipartFile file, String organization, String published);

	public FileResourceInfo getPictureInfoByfileId(String fileId);

}
