package com.eseasky.core.framework.AuthService.UserTools.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration//初始化一个当前类，并将它放入到Spring的ioc容器中
@ConfigurationProperties(prefix = "database.com")
public class DatabaseProperties {

    private String ip;
    private String user;
    private String password;
    private String databaseName;
    private String savePath;

    @Value("${database.backup}")
    private String backupCommand;

    @Value("${database.restore}")
    private String restoreCommand;
}
