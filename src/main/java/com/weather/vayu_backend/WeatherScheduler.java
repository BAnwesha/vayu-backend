package com.weather.vayu_backend;

import com.weather.vayu_backend.WeatherWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherScheduler {

    private final WeatherWebSocketHandler webSocketHandler;

    public WeatherScheduler(WeatherWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }


    @Scheduled(fixedRate = 60000)
    public void sendPeriodicUpdates() {
        System.out.println("Scheduler triggered: Updating all clients with their specific cities...");
        webSocketHandler.updateAllClients();
    }
}