package com.eseasky.core.framework.AuthService.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration//初始化一个当前类，并将它放入到Spring的ioc容器中
@ConfigurationProperties(prefix = "database.com")
public class DatabaseProperties {

    private String ip = "192.168.1.232";
    private String user = "mnet";
    private String password = "mnet@123";
    private String databaseName = "mnetbak";
    private String savePath = "/home/mnet/mysqlbak";

//    @Value("${database.backup}")
//    private String backupCommand = "/home/mnet/mariadb/bin/mysqldump --defaults-file=/home/mnet/mariadb/my.cnf -h192.168.1.232 -umnet_business -p'mnet@123'";
//
//    @Value("${database.restore}")
//    private String restoreCommand = "/home/mnet/mariadb/bin/mysql --defaults-file=/home/mnet/mariadb/my.cnf -h192.168.1.232 -umnet_business -p'mnet@123'";
}
