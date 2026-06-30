package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/weather")
public class WeatherResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WeatherInfo getWeather(@QueryParam("city") String city) {
        if (city == null || city.isEmpty()) {
            return new WeatherInfo(null, null, null, null, "Error: city parameter is required");
        }
        return getWeatherByCityName(city);
    }

    @GET
    @Path("/forecast")
    @Produces(MediaType.APPLICATION_JSON)
    public WeatherForecast getForecast(@QueryParam("city") String city, @QueryParam("days") int days) {
        if (city == null || city.isEmpty()) {
            return new WeatherForecast(null, days, "Error: city parameter is required");
        }
        if (days <= 0 || days > 7) {
            return new WeatherForecast(city, days, "Error: days must be between 1 and 7");
        }
        return generateForecast(city, days);
    }

    private WeatherInfo getWeatherByCityName(String city) {
        // Mock data - in production, you would call a real weather API
        switch (city.toLowerCase()) {
            case "paris":
                return new WeatherInfo("Paris", 22.5, "Partly Cloudy", 65);
            case "london":
                return new WeatherInfo("London", 18.0, "Rainy", 75);
            case "new york":
                return new WeatherInfo("New York", 25.0, "Sunny", 50);
            case "tokyo":
                return new WeatherInfo("Tokyo", 28.0, "Sunny", 60);
            default:
                return new WeatherInfo(city, 20.0, "Unknown", 70);
        }
    }

    private WeatherForecast generateForecast(String city, int days) {
        String[] conditions = {"Sunny", "Cloudy", "Rainy", "Partly Cloudy"};
        WeatherForecast.DayForecast[] forecasts = new WeatherForecast.DayForecast[days];

        for (int i = 0; i < days; i++) {
            forecasts[i] = new WeatherForecast.DayForecast(
                "Day " + (i + 1),
                20.0 + (i * 2),
                conditions[i % conditions.length],
                30 + (i * 5)
            );
        }

        return new WeatherForecast(city, days, forecasts, null);
    }

    public static class WeatherInfo {
        public String city;
        public double temperature;
        public String condition;
        public int humidity;
        public String error;

        public WeatherInfo(String city, Double temperature, String condition, Integer humidity) {
            this.city = city;
            this.temperature = temperature != null ? temperature : 0;
            this.condition = condition;
            this.humidity = humidity != null ? humidity : 0;
            this.error = null;
        }

        public WeatherInfo(String city, Double temperature, String condition, Integer humidity, String error) {
            this.city = city;
            this.temperature = temperature != null ? temperature : 0;
            this.condition = condition;
            this.humidity = humidity != null ? humidity : 0;
            this.error = error;
        }
    }

    public static class WeatherForecast {
        public String city;
        public int days;
        public DayForecast[] forecast;
        public String error;

        public WeatherForecast(String city, int days, String error) {
            this.city = city;
            this.days = days;
            this.forecast = null;
            this.error = error;
        }

        public WeatherForecast(String city, int days, DayForecast[] forecast, String error) {
            this.city = city;
            this.days = days;
            this.forecast = forecast;
            this.error = error;
        }

        public static class DayForecast {
            public String day;
            public double temperature;
            public String condition;
            public int humidity;

            public DayForecast(String day, double temperature, String condition, int humidity) {
                this.day = day;
                this.temperature = temperature;
                this.condition = condition;
                this.humidity = humidity;
            }
        }
    }
}