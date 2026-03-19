package com.weather.vayu_backend.model;

import java.util.List;

public class WeatherResponse {

    private String name;
    private Main main;
    private List<Weather> weather;
    private int visibility;
    private Wind wind;
    private Sys sys;
    private long timezone;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys(){
        return sys;
    }

    public void setSys(Sys sys){
        this.sys = sys;
    }

    public long getTimezone(){
        return timezone;
    }

    public void setTimezone(long timezone){
        this.timezone = timezone;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private double humidity;
        private double pressure;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getPressure(){
            return pressure;
        }

        public void setPressure(double pressure){
            this.pressure = pressure;
        }

        public double getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(double feels_like) {
            this.feels_like = feels_like;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }
    }

    public static class Weather {
        private String main;
        private String description;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Wind {
        private double speed;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    public static class Sys {
        private String country;
        private long sunrise;
        private long sunset;

        public String getCountry(){
            return country;
        }

        public void setCountry(String country){
            this.country = country;
        }

        public long getSunrise(){
            return sunrise;
        }

        public void setSunrise(long sunrise){
            this.sunrise = sunrise;
        }

        public long getSunset(){
            return sunset;
        }

        public void setSunset(long sunset){
            this.sunset = sunset;
        }
    }
}
