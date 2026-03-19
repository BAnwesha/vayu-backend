package com.weather.vayu_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.vayu_backend.model.WeatherResponse;
import com.weather.vayu_backend.service.WeatherService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeatherWebSocketHandler extends TextWebSocketHandler {

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    private final Map<WebSocketSession, String> userCityMap = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, String> userWeatherCache = new ConcurrentHashMap<>();

    public WeatherWebSocketHandler(WeatherService weatherService, ObjectMapper objectMapper) {
        this.weatherService = weatherService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userCityMap.put(session, "");
        System.out.println("New connection: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String city = message.getPayload();

        userCityMap.put(session, city);

        userWeatherCache.remove(session);

        try {
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            String jsonOutput = objectMapper.writeValueAsString(weatherResponse);

            session.sendMessage(new TextMessage(jsonOutput));
            userWeatherCache.put(session, jsonOutput);

        } catch (Exception e) {
            session.sendMessage(new TextMessage("{\"error\": \"City not found or API error\"}"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userCityMap.remove(session);

        userWeatherCache.remove(session);

        System.out.println("Disconnected: " + session.getId());
    }

    public void updateAllClients() {
        for (Map.Entry<WebSocketSession, String> entry : userCityMap.entrySet()) {
            WebSocketSession session = entry.getKey();
            String city = entry.getValue();

            if (session.isOpen()) {
                try {
                    WeatherResponse weatherResponse = weatherService.getWeather(city);
                    String newJsonOutput = objectMapper.writeValueAsString(weatherResponse);

                    String oldJsonOutput = userWeatherCache.get(session);

                    if (!newJsonOutput.equals(oldJsonOutput)) {
                        session.sendMessage(new TextMessage(newJsonOutput));
                        userWeatherCache.put(session, newJsonOutput);
                        System.out.println("Weather changed for " + city + ". Pushed update to session " + session.getId());
                    }

                } catch (Exception e) {
                    System.err.println("Error updating client " + session.getId());
                }
            }
        }
    }
}