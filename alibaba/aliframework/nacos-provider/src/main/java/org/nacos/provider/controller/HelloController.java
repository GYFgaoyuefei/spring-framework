package org.nacos.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String hello(@RequestParam("name") String name) {
		return "Hello " + name + ", this is nacos-provider message.";
	}
}
