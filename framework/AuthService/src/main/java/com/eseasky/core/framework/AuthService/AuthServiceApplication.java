package com.eseasky.core.framework.AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
* OAuth2认证授权服务
* @ EnableDiscoveryClient 启用服务注册发现
*/
@SpringBootApplication
@EnableResourceServer //开启资源服务，因为程序需要对外暴露获取token的API接口
@EnableEurekaClient //开启Eureka Client
@EnableScheduling
//@EnableOrganizeGranted
@EnableJpaRepositories(basePackages ={"com.eseasky.core.starters.organization.persistence.repository.**"
    ,"com.eseasky.core.framework.AuthService.module.**"})
@EntityScan(basePackages ={"com.eseasky.core.starters.organization.persistence.model.**"
    ,"com.eseasky.core.framework.AuthService.module.**"})
public class AuthServiceApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AuthServiceApplication.class, args);
    }
}
