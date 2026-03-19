package com.weather.vayu_backend.controller;

import com.weather.vayu_backend.model.WeatherResponse;
import com.weather.vayu_backend.service.WeatherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/{city}")
    public WeatherResponse getCityWeather(@PathVariable String city) {
        return weatherService.getWeather(city);
    }

}
