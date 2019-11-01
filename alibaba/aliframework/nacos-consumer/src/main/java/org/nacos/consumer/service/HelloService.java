package org.nacos.consumer.service;

import org.nacos.consumer.sentinels.SentinelHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value="nacos-provider",fallback = SentinelHandler.class)
public interface HelloService {
	@RequestMapping("/hello")
	String hello(@RequestParam("name") String name);
}
