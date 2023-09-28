package com.example.weatherforecast.models.current;

public class CurrentWeather {

    /* Variables obtained from the WeatherAPI JSON response */
    private double temp_c;
    private CurrentCondition condition;
    private CurrentAirQuality air_quality;

    /* Getters and Setters */
    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    public CurrentCondition getCondition() {
        return condition;
    }

    public void setCondition(CurrentCondition condition) {
        this.condition = condition;
    }

    public CurrentAirQuality getAir_quality() {
        return air_quality;
    }

    public void setAir_quality(CurrentAirQuality air_quality) {
        this.air_quality = air_quality;
    }
}
