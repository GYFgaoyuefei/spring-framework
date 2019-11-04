package org.aligateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@SpringBootApplication
@EnableDiscoveryClient
//@NacosPropertySource(dataId = "example", autoRefreshed = true)
@RefreshScope
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}