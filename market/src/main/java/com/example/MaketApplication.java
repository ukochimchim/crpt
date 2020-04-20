package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = { "com.example.controller" })
public class MaketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaketApplication.class, args);
	}

}
