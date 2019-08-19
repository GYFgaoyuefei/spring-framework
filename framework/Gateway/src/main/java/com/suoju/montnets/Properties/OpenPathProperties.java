package com.suoju.montnets.Properties;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="spring.cloud.gateway.open-path")
public class OpenPathProperties {
	private String paths;
}
