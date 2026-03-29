package com.weather.vayu_backend.service;

import com.weather.vayu_backend.OpenWeatherClient;
import com.weather.vayu_backend.model.WeatherResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class WeatherService {
//    @PostConstruct
//    public void forceCircuitBreakerTest() {
//        System.out.println("\n--- ISOLATION TEST ---");
//        try {
//            // Call the dummy method instead of the real one
//            weatherClient.explode("London");
//        } catch (Exception e) {
//            System.out.println("Uncaught Error: " + e.getMessage());
//        }
//        System.out.println("--- TEST COMPLETE ---\n");
//    }

    private final OpenWeatherClient weatherClient;

    public WeatherService(OpenWeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }
    @CircuitBreaker(name = "openWeatherApi", fallbackMethod = "weatherFallback")
    public WeatherResponse getWeather(String city){
        WeatherResponse response = weatherClient.getWeather(city);
        return response;
    }
    public String formatTime(long unixTime, long timezoneOffset) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        ZoneOffset offset = ZoneOffset.ofTotalSeconds((int) timezoneOffset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return instant.atOffset(offset).format(formatter);
    }
}
