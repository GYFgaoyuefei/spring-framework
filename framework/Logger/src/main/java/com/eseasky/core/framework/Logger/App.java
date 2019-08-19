package com.eseasky.core.framework.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
public class App 
{
	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
