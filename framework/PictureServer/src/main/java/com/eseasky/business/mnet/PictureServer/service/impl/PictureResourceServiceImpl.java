package com.eseasky.business.mnet.PictureServer.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.eseasky.protocol.picture.entity.VO.NewFileResourceInfo;
import org.apache.catalina.webresources.FileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eseasky.business.mnet.PictureServer.module.model.FileResourceInfo;
import com.eseasky.business.mnet.PictureServer.module.repository.PictureResourceRepository;
import com.eseasky.business.mnet.PictureServer.service.PictureResourceService;
import com.eseasky.core.starters.system.exception.errors.BaseHandlerException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PictureResourceServiceImpl implements PictureResourceService {

    @Autowired
    PictureResourceRepository pictureResourceRepository;

    @Value("${custom.upload.absolute-path}")
    private String savaPath;

    @Override
    public FileResourceInfo uploadSingle(String resourceType, MultipartFile file, String organization) {
        InputStream in = null;
        FileOutputStream out = null;
        try {

            //获取文件的MD5值
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            log.info(hashString);

//			//根据文件的MD5值查询已有的数据
            FileResourceInfo pictureResourceOld = pictureResourceRepository.findByFileMd5AndResourceType(hashString, resourceType);
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
            out = new FileOutputStream(savaPath + File.separator + imageName);// 指定要写入的图片路径
            int n = 0;
            byte[] bb = new byte[1024];// 存储每次读取的内容
            while ((n = in.read(bb)) != -1) {
                out.write(bb, 0, n);// 将读取的内容，写入到输出流当中
            }

            FileResourceInfo pictureResource = new FileResourceInfo();
            pictureResource.setFileId(uuid);
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
    public void getPictureResourceByFilename(String fileName, HttpServletResponse response) {
        InputStream inputStream = null;
        try {
            File file;
            if ("null".equals(fileName) || "".equals(fileName)) {
                inputStream = this.getClass().getResourceAsStream("/delault.png");
            } else {
                file = new File(savaPath + File.separator + fileName);
                if (file == null || file.exists() == false) {
                    inputStream = this.getClass().getResourceAsStream("/delault.png");
                }else{
                    inputStream = new FileInputStream(file);
                }
            }

            if (inputStream != null) {
                // 获取文件后缀
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
                response.setContentType(getImageType(prefix));
                OutputStream outputStream = response.getOutputStream();
             // 得到文件大小
//              int i = inputStream.available();
                int len = 0;  
                byte[] data = new byte[1024 * 10];
              // 读数据
                while ((len = inputStream.read(data)) != -1){  
                	outputStream.write(data,0,len);  
                } 
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseHandlerException(500, e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private String getImageType(String prefix) {
        switch (prefix) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "bmp":
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            default:
                return MediaType.IMAGE_PNG_VALUE;
        }
    }

    @Override
    public void getPictureResourceByFileId(String fileId, HttpServletResponse response) {
        FileResourceInfo pictureResource = pictureResourceRepository.findByFileId(fileId);
        if (pictureResource != null && pictureResource.getFileName() != null && !"".equals(pictureResource.getFileName())) {
            getPictureResourceByFilename(pictureResource.getFileName(), response);
        } else {
            throw new BaseHandlerException(500, fileId + " not exists");
        }
    }

    @Override
    public FileResourceInfo uploadByfileId(String fileId, String resourceType, MultipartFile file, String organization) {
        InputStream in = null;
        FileOutputStream out = null;
        try {

            FileResourceInfo pictureResource = pictureResourceRepository.findByFileId(fileId);
            if (pictureResource == null) {
                throw new BaseHandlerException(500, fileId + " not exists");
            }

            //获取文件的MD5值
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            log.info(hashString);

//			//根据文件的MD5值查询已有的数据
            FileResourceInfo pictureResourceOld = pictureResourceRepository.findByFileMd5AndResourceType(hashString, resourceType);
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
            out = new FileOutputStream(savaPath + File.separator + imageName);// 指定要写入的图片路径
            int n = 0;
            byte[] bb = new byte[1024];// 存储每次读取的内容
            while ((n = in.read(bb)) != -1) {
                out.write(bb, 0, n);// 将读取的内容，写入到输出流当中
            }

//			pictureResource.setFileId(fileId);
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
    public FileResourceInfo getPictureInfoByfileId(String fileId) {
        return pictureResourceRepository.findByFileId(fileId);
    }


    @SuppressWarnings("unused")
    @Override
    public NewFileResourceInfo getMultipartFileByFileId(String fileId) {
        NewFileResourceInfo newFileResourceInfo = new NewFileResourceInfo();
        HashMap<String, byte[]> param = new HashMap<>();
        FileResourceInfo pictureResource = pictureResourceRepository.findByFileId(fileId);
        if (pictureResource != null && pictureResource.getFileName() != null && !"".equals(pictureResource.getFileName())) {
            FileInputStream inputStream = null;
            try {
                File file = new File(savaPath + File.separator + pictureResource.getFileName());
                if (file == null || file.exists() == false) {
                    throw new BaseHandlerException(500, pictureResource.getFileName() + " not exists");
                }
                inputStream = new FileInputStream(file);
                if (inputStream != null) {
                    MultipartFile multipartFile = new MockMultipartFile(file.getAbsolutePath(), pictureResource.getFileName(), null, inputStream);
                    String fileName = multipartFile.getOriginalFilename();
                    // 获取文件后缀
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    // 用uuid作为文件名，防止生成的临时文件重复
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    final String filename = uuid + prefix;
                    ByteArrayResource contentsAsResource;
                    contentsAsResource = new ByteArrayResource(multipartFile.getBytes()) {
                        @Override
                        public String getFilename() {
                            return filename;
                        }
                    };

                    param.put("file", multipartFile.getBytes());
                    newFileResourceInfo.setParam(param);
                    newFileResourceInfo.setFileName(fileName);
                    return newFileResourceInfo;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new BaseHandlerException(500, e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } else {
            //throw new BaseHandlerException(500, fileId+" not exists");
            return null;
        }
        return null;

    }


}