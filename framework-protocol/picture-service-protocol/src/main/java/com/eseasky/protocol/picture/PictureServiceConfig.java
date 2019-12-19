package com.eseasky.protocol.picture;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eseasky.core.starters.feign.wrapper.FeignClientFactory;
import com.eseasky.protocol.picture.protocol.PictureServiceFeign;

import feign.form.spring.SpringFormEncoder;

@ConditionalOnBean(FeignClientFactory.class)
@Configuration
public class PictureServiceConfig {
	@Autowired
	FeignClientFactory factory;
	
	@Autowired
	ObjectFactory<HttpMessageConverters> messageConverters;
	
	@Bean
	public PictureServiceFeign pictureServiceFeign() {
		return factory.multipartclientFeign(PictureServiceFeign.class,new SpringFormEncoder(new SpringEncoder(messageConverters)));
	}
	
//	@Bean
//	public Encoder feignEncoder() {
//		return new SpringFormEncoder(new SpringEncoder(messageConverters));
//	}
}
