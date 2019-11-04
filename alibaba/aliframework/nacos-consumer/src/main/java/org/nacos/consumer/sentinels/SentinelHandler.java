package org.nacos.consumer.sentinels;

import org.nacos.consumer.service.HelloService;
import org.springframework.stereotype.Component;


@Component
public class SentinelHandler implements HelloService {

	@Override
	public String hello(String name) {
		// TODO Auto-generated method stub		
		return "Sentinel {由于你的访问次数太多，已为你限流、您已进入保护模式，请稍后再试！}>>>熔断处理函数";
	}

}
