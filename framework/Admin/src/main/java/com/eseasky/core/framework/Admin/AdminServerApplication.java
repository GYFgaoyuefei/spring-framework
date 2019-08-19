package com.eseasky.core.framework.Admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableAdminServer
@Log4j2
@EnableEurekaClient
public class AdminServerApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AdminServerApplication.class, args);
        log.info("AdminServerApplication启动!");
    }
}
