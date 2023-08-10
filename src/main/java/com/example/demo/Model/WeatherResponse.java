package com.example.demo.Model;

public class WeatherResponse {

    /* Get current weather element from JSON response */
    private CurrentWeather current;

    /* Getter and Setter */
    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }
}
