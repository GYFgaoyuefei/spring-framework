package com.eseasky.protocol.picture.protocol;

import com.eseasky.core.starters.feign.wrapper.config.Feign;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.picture.entity.VO.NewFileResourceInfo;
import com.eseasky.protocol.picture.entity.VO.PictureInfoVO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Feign(serviceName="PictureServer")
public interface PictureServiceFeign {
	
	@RequestLine("POST /uploadSingle")
	@Headers({"Content-Type: multipart/form-data"})
//	@RequestMapping(method = RequestMethod.POST, value = "/uploadSingle",
//            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResultModel<PictureInfoVO> uploadSingle(
			@Param("resourceType") String resourceType, 
			@Param("organization") String organization, 
			@Param("file") MultipartFile file);

	@RequestLine("GET /getImagesByFileId")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public Response getImagesByFileId(@Param("fileId") String fileId);

	@RequestLine("GET /images/{fileName}")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public Response getImagesByFilename(@Param("fileName") String fileName);

	
	@RequestLine("POST /uploadByfileId")
	@Headers({"Content-Type: multipart/form-data"})
	public ResultModel<PictureInfoVO> uploadByfileId(
			@Param("fileId") String fileId, 
			@Param("resourceType") String resourceType, 
			@Param("organization") String organization, 
			@Param("file") MultipartFile file);
	
	@RequestLine("POST /getPictureInfoByfileId")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<PictureInfoVO> getPictureInfoByfileId(@RequestBody String fileId);
	
	@RequestLine("POST /getMultipartFileByFileId")
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public ResultModel<NewFileResourceInfo> getMultipartFileByFileId(@RequestBody String fileId);
	
}
