package com.eseasky.core.framework.PictureServer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eseasky.core.framework.PictureServer.module.model.PictureResource;
import com.eseasky.core.framework.PictureServer.service.PictureResourceService;
import com.eseasky.global.entity.ResultModel;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
//@RequestMapping(value="/pictureserver")
public class PictureServerController {
	
	@Autowired
	PictureResourceService pictureResourceService;

	
	@PostMapping(value = "/uploadSingle")
	public ResultModel<PictureResource> uploadSingle(@RequestParam String resourceType, @RequestParam MultipartFile file, @RequestParam String organization) {
		ResultModel<PictureResource> msgReturn = new ResultModel<PictureResource> ();
		try {
			log.info(resourceType);
			msgReturn.setData(pictureResourceService.uploadSingle(resourceType, file, organization));
		} catch (Exception e) {
			e.printStackTrace();
			msgReturn.setSubCode(500);
			msgReturn.setMessage(e.getMessage());
		}
		return msgReturn;
	}
	
	@GetMapping(value = "/getPictureResourceByFilename")
	public ResultModel<PictureResource> getPictureResourceByFilename(@RequestParam String fileName, HttpServletRequest request, HttpServletResponse response) {
		ResultModel<PictureResource> msgReturn = new ResultModel<PictureResource> ();
		try {
			log.info(fileName);
			msgReturn.setData(pictureResourceService.getPictureResourceByFilename(fileName, response));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			msgReturn.setSubCode(500);
			msgReturn.setMessage(e.getMessage());
			return msgReturn;
		}
	}
	
	@GetMapping(value = "/images/{fileName}")
	public ResultModel<PictureResource> imagesByFilename(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {
		ResultModel<PictureResource> msgReturn = new ResultModel<PictureResource> ();
		try {
			log.info(fileName);
			pictureResourceService.getPictureResourceByFilename(fileName, response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			msgReturn.setSubCode(500);
			msgReturn.setMessage(e.getMessage());
			return msgReturn;
		}
	}
}
