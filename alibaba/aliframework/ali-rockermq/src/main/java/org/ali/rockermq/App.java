package org.ali.rockermq;

import org.ali.rockermq.consumer.Mysink;
import org.ali.rockermq.producer.Mysource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({ Mysource.class, Mysink.class })
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
