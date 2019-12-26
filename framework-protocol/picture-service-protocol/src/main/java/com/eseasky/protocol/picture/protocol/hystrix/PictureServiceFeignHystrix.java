package com.eseasky.protocol.picture.protocol.hystrix;

import org.springframework.web.multipart.MultipartFile;

import com.eseasky.core.starters.feign.wrapper.fallbacks.IHystrix;
import com.eseasky.global.entity.ResultModel;
import com.eseasky.protocol.picture.entity.VO.NewFileResourceInfo;
import com.eseasky.protocol.picture.entity.VO.PictureInfoVO;
import com.eseasky.protocol.picture.protocol.PictureServiceFeign;

import feign.Response;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PictureServiceFeignHystrix implements PictureServiceFeign, IHystrix {
	
	private Throwable throwable;

	@Override
	public Throwable setThrowable(Throwable throwable) {
		// TODO Auto-generated method stub
		log.error(throwable.getMessage());
		return throwable;
	}

	@Override
	public ResultModel<PictureInfoVO> uploadSingle(String resourceType, String published, String organization, MultipartFile file) {
		return null;
	}

	@Override
	public Response getImagesByFileId(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getImagesByFilename(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultModel<PictureInfoVO> uploadByfileId(String fileId, String resourceType, String organization, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultModel<PictureInfoVO> getPictureInfoByfileId(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultModel<NewFileResourceInfo> getMultipartFileByFileId(String fileId) {
		// TODO Auto-generated method stub
		ResultModel<NewFileResourceInfo> resultModel = new ResultModel<NewFileResourceInfo>();
		resultModel.setSubCode(500);
		resultModel.setMessage(throwable.getLocalizedMessage());
		return null;
	}


}
