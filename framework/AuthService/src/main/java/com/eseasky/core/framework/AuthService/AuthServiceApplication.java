package com.eseasky.core.framework.AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
* OAuth2认证授权服务
* @ EnableDiscoveryClient 启用服务注册发现
*/
@SpringBootApplication
@EnableResourceServer //开启资源服务，因为程序需要对外暴露获取token的API接口
@EnableEurekaClient //开启Eureka Client
public class AuthServiceApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AuthServiceApplication.class, args);
    }
}
