package com.weather.vayu_backend;

import com.weather.vayu_backend.model.WeatherResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherClient {

    private final RestTemplate restTemplate;
    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;
    public OpenWeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "openWeatherApi", fallbackMethod = "weatherFallback")
    @Cacheable(value = "weatherCache", key = "#city")
    public WeatherResponse getWeather(String city) {
        // We added the print statement back here
        System.out.println("Attempting to call OpenWeatherMap API for: " + city);

        String finalUrl =apiUrl.replace("{city}",city).replace("{apiKey}",apiKey);

        return restTemplate.getForObject(finalUrl, WeatherResponse.class);
    }

    // Changed Throwable to Exception so Spring recognizes it
    public WeatherResponse weatherFallback(String city, Throwable e) {
        // We print the exact error message so you can see it working
        System.out.println("Fallback triggered! Blocked error: " + e.getMessage());

        return new WeatherResponse();
    }

}