package com.eseasky.protocol.system;

import com.eseasky.core.starters.feign.wrapper.FeignClientFactory;
import com.eseasky.protocol.system.protocol.SystemServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnBean(FeignClientFactory.class)
@Configuration
public class SystemServiceConfig {
    @Autowired
    FeignClientFactory factory;
    @Bean
    public SystemServiceFeign systemServiceFeign() {
        return factory.clientFeignContract(SystemServiceFeign.class);
    }
}
