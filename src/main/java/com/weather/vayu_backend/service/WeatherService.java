package com.weather.vayu_backend.service;

import com.weather.vayu_backend.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city){
        String finalUrl = apiUrl.replace("{city}",city).replace("{apiKey}",apiKey);
        WeatherResponse response= restTemplate.getForObject(finalUrl, WeatherResponse.class);
        return response;
    }

    public String formatTime(long unixTime, long timezoneOffset) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        ZoneOffset offset = ZoneOffset.ofTotalSeconds((int) timezoneOffset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return instant.atOffset(offset).format(formatter);
    }
}
