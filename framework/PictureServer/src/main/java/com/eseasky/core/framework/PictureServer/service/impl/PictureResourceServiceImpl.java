package com.eseasky.core.framework.PictureServer.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eseasky.core.framework.PictureServer.module.model.PictureResource;
import com.eseasky.core.framework.PictureServer.module.repository.PictureResourceRepository;
import com.eseasky.core.framework.PictureServer.service.PictureResourceService;
import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PictureResourceServiceImpl implements PictureResourceService {

	@Autowired
	PictureResourceRepository pictureResourceRepository;

    @Value("${upload.savaPath}")
    private String savaPath;
	
	@Override
	public PictureResource uploadSingle(String resourceType,  MultipartFile file, String organization) {
        InputStream in = null;
        FileOutputStream out = null;
		try {
			
			//获取文件的MD5值
			byte[] uploadBytes = file.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(uploadBytes);
			String hashString = new BigInteger(1, digest).toString(16);
			log.info(hashString);
			
			//根据文件的MD5值查询已有的数据
			PictureResource pictureResourceOld = pictureResourceRepository.findByFileMd5(hashString);
			if (pictureResourceOld != null) {
				return pictureResourceOld;
			}

            //将文件写入本地
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            // 用uuid作为文件名，防止生成的临时文件重复
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String imageName = uuid + prefix;
//            String absPath = savaPath +File.separator+ imageName;

			//上传文件流写入服务中
            in = file.getInputStream();
            out = new FileOutputStream(savaPath +File.separator+ imageName);// 指定要写入的图片路径
            int n = 0;
            byte[] bb = new byte[1024];// 存储每次读取的内容
            while ((n = in.read(bb)) != -1) {
                out.write(bb, 0, n);// 将读取的内容，写入到输出流当中
            }
			
			PictureResource pictureResource = new PictureResource();
			pictureResource.setFileName(imageName);
			pictureResource.setFileMd5(hashString);
			pictureResource.setResourceType(resourceType);
			pictureResource.setResourcePath(savaPath);
			pictureResource.setOrganization(organization);
			return pictureResourceRepository.save(pictureResource);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭输入输出流
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public PictureResource getPictureResourceByFilename(String fileName, HttpServletResponse response){
		try {
			File file = new File(savaPath +File.separator+fileName);
			if (file == null || file.exists() == false) {
				throw new BaseHandlerException(500, fileName+" not exists");
			}
			FileInputStream inputStream = new FileInputStream(file);
			if (inputStream != null) {
				// 得到文件大小
				int i = inputStream.available();
				byte[] data = new byte[i];
				// 读数据
				inputStream.read(data);
				inputStream.close();
//				response.setContentType("image/png");
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(data);
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BaseHandlerException(500, e.getMessage());
		}
		
		return null;
	}

}
