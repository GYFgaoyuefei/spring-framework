package org.nacos.consumer.controller;

import org.nacos.consumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope // 打开动态刷新功能
public class HelloController {
	@Autowired
	private HelloService helloService;

	@Value("${user.names}")
	String userName;

	@Value("${user.age}")
	int age;

	@RequestMapping("/hello/{name}")
	public String hello(@PathVariable String name) {
		return helloService.hello(name+userName);
	}
}
