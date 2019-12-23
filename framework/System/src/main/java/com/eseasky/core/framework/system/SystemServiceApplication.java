package com.eseasky.core.framework.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
@EnableJpaRepositories(basePackages ={"com.eseasky.starter.dictionary.module.**"})
@EntityScan(basePackages ={"com.eseasky.starter.dictionary.module.**"})
public class SystemServiceApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(SystemServiceApplication.class, args);
    }
}
