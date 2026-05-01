package com.weather.vayu_backend.service;

import com.weather.vayu_backend.OpenWeatherClient;
import com.weather.vayu_backend.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class WeatherService {

    private final OpenWeatherClient weatherClient;
    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(OpenWeatherClient weatherClient, RestTemplate restTemplate) {
        this.weatherClient = weatherClient;
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "openWeatherApi", fallbackMethod = "weatherFallback")
    public WeatherResponse getWeather(String city) {

        // STEP 1: Get current weather
        WeatherResponse response = weatherClient.getWeather(city);


        // STEP 2: Fetch 5-day/3-hour forecast using free /forecast endpoint
        if (response != null && response.getCoord() != null) {
            double lat = response.getCoord().getLat();
            double lon = response.getCoord().getLon();

            String forecastUrl = String.format(
                    "https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&units=metric&appid=%s",
                    lat, lon, apiKey
            );

            try {
                WeatherResponse forecastData = restTemplate.getForObject(forecastUrl, WeatherResponse.class);
                if (forecastData != null && forecastData.getList() != null) {
                    // STEP 3: Merge forecast list into main response
                    response.setList(forecastData.getList());
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch forecast: " + e.getMessage());
            }
        }

        return response;
    }

    public String formatTime(long unixTime, long timezoneOffset) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        ZoneOffset offset = ZoneOffset.ofTotalSeconds((int) timezoneOffset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return instant.atOffset(offset).format(formatter);
    }

    public WeatherResponse weatherFallback(String city, Throwable t) {
        System.err.println("Circuit Breaker triggered for city: " + city + ". Reason: " + t.getMessage());
        t.printStackTrace();
        return null;
    }

}