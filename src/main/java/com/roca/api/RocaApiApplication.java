package com.roca.api;

import com.roca.api.config.property.ApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperty.class)
public class RocaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocaApiApplication.class, args);
	}

}
