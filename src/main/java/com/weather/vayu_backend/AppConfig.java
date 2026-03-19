package com.weather.vayu_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Tells Spring this is a settings file
public class AppConfig {

    @Bean // Tells Spring to create this object and manage it
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
