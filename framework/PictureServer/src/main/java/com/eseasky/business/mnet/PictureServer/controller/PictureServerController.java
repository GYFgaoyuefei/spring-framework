package com.eseasky.business.mnet.PictureServer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.eseasky.business.mnet.PictureServer.module.model.FileResourceInfo;
import com.eseasky.business.mnet.PictureServer.service.PictureResourceService;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.picture.entity.VO.NewFileResourceInfo;
import com.eseasky.protocol.picture.entity.VO.PictureInfoVO;
import feign.Param;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Log4j2
@RestController
public class PictureServerController {

    @Autowired
    PictureResourceService pictureResourceService;


    @PostMapping(value = "/uploadSingle")
    public ResultModel<PictureInfoVO> uploadSingle(@RequestParam String resourceType, @RequestParam String published, @RequestParam String organization, @RequestPart MultipartFile file, @RequestParam String width, @RequestParam String height) {
        ResultModel<PictureInfoVO> msgReturn = new ResultModel<>();
        try {
            log.info("请求参数[resourceType={}, organization={}]", resourceType, organization);
            FileResourceInfo uploadSingle = pictureResourceService.uploadSingle(resourceType, file, organization, published,width,height);
            if (uploadSingle != null) {
                log.info("响应参数[{}]", JSON.toJSONString(uploadSingle));
                PictureInfoVO pictureInfoVO = JSON.parseObject(JSON.toJSONString(uploadSingle), PictureInfoVO.class, Feature.OrderedField);
                log.info("响应参数[{}]", JSON.toJSONString(pictureInfoVO));
                msgReturn.setData(pictureInfoVO);
            } else {
                msgReturn.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgReturn.setSubCode(500);
            msgReturn.setMessage(e.getMessage());
        }
        return msgReturn;
    }

    @GetMapping(value = "/getImagesByFileId")
    public void getImagesByFileId(@RequestParam String fileId, HttpServletResponse response) {
        try {
            log.info("请求参数[{}]", JSON.toJSONString(fileId));
            pictureResourceService.getPictureResourceByFileId(fileId, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/access/image/{fileId}")
    public void accessImage(@PathVariable String fileId, HttpServletResponse response) {
        try {
            log.info("请求参数[{}]", JSON.toJSONString(fileId));
            pictureResourceService.getPictureResourceByFileId(fileId, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/getMultipartFileByFileId")
    public ResultModel<NewFileResourceInfo> getMultipartFileByFileId(@RequestBody String fileId) {

//        fileId = fileId.substring(fileId.indexOf(":") + 2, fileId.lastIndexOf("\""));
        ResultModel<NewFileResourceInfo> msgReturn = new ResultModel<>();
        try {
            log.info("请求参数[{}]", JSON.toJSONString(fileId));
            NewFileResourceInfo map = pictureResourceService.getMultipartFileByFileId(fileId);
            if (map == null) {
                msgReturn.setSubCode(301);
                msgReturn.setMessage("获取文件失败");
            } else {
                msgReturn.setData(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msgReturn.setSubCode(500);
            msgReturn.setMessage(e.getMessage());
        }
        return msgReturn;
    }

    @GetMapping(value = "/images/{fileName}")
    public void getImagesByFilename(@PathVariable String fileName, HttpServletResponse response) {
        try {
            log.info("请求参数[{}]", JSON.toJSONString(fileName));
            pictureResourceService.getPictureResourceByFilename(fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/uploadByfileId")
    public ResultModel<PictureInfoVO> uploadByfileId(
    		@RequestParam String published,
            @RequestParam String fileId, @RequestParam String resourceType,
            @RequestParam String organization, @RequestParam MultipartFile file) {
        ResultModel<PictureInfoVO> msgReturn = new ResultModel<>();
        try {
            log.info("请求参数[fileId={}, resourceType={}, organization={}]", fileId, resourceType, organization);
            FileResourceInfo uploadSingle = pictureResourceService.uploadByfileId(fileId, resourceType, file, organization, published);
            log.info("响应参数[{}]", JSON.toJSONString(uploadSingle));
            if (uploadSingle != null) {
                PictureInfoVO pictureInfoVO = JSON.parseObject(JSON.toJSONString(uploadSingle), PictureInfoVO.class, Feature.OrderedField);
                log.info("响应参数[{}]", JSON.toJSONString(pictureInfoVO));
                msgReturn.setData(pictureInfoVO);
            } else {
                msgReturn.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgReturn.setSubCode(500);
            msgReturn.setMessage(e.getMessage());
        }
        return msgReturn;
    }

    @PostMapping(value = "/getPictureInfoByfileId")
    public ResultModel<PictureInfoVO> getPictureInfoByfileId(@Param("fileId") String fileId) {
        ResultModel<PictureInfoVO> msgReturn = new ResultModel<>();
        try {
            log.info("请求参数[fileId={}]", fileId);
            FileResourceInfo fileResourceInfo = pictureResourceService.getPictureInfoByfileId(fileId);
            log.info("响应参数[{}]", JSON.toJSONString(fileResourceInfo));
            if (fileResourceInfo != null) {
                PictureInfoVO pictureInfoVO = JSON.parseObject(JSON.toJSONString(fileResourceInfo), PictureInfoVO.class, Feature.OrderedField);
                BeanUtils.copyProperties(pictureInfoVO, fileResourceInfo);
                log.info("响应参数[{}]", JSON.toJSONString(pictureInfoVO));
                msgReturn.setData(pictureInfoVO);
            } else {
                msgReturn.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgReturn.setSubCode(500);
            msgReturn.setMessage(e.getMessage());
        }
        return msgReturn;
    }

}
