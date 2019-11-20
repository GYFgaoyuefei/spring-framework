package com.eseasky.core.framework.SecurityManage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(basePackages ={"com.eseasky.starter.security.module.**"})
@EntityScan(basePackages ={"com.eseasky.starter.security.module.**"})
public class SecurityManageApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(SecurityManageApp.class, args);
    }
}
