package com.weather.vayu_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableScheduling
public class VayuBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VayuBackendApplication.class, args);
	}
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}

