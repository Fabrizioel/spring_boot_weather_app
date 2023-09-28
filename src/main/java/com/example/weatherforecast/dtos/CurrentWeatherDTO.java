package com.example.weatherforecast.dtos;

import java.util.Optional;
import java.util.OptionalInt;

public class CurrentWeatherDTO {
    
    private String icon;
    private String weatherText;
    private String city;
    private double temperature;
    private boolean airQuality;
    OptionalInt airQualityIndex;
    Optional<String> airQualityText;
    Optional<String> airQualityColor;

    public CurrentWeatherDTO(String icon, String weatherText, String city, double temperature, boolean airQuality, OptionalInt airQualityIndex, Optional<String> airQualityText, Optional<String> airQualityColor) {
  
        this.icon = icon;
        this.weatherText = weatherText;
        this.city = city;
        this.temperature = temperature;
        this.airQuality = airQuality;
        if (airQualityIndex.isPresent()) this.airQualityIndex = OptionalInt.of(airQualityIndex.getAsInt());
        if (airQualityText.isPresent()) this.airQualityText = Optional.of(airQualityText.get());
        if (airQualityColor.isPresent())this.airQualityColor = Optional.of(airQualityColor.get());
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(boolean airQuality) {
        this.airQuality = airQuality;
    }

    public OptionalInt getAirQualityIndex() {
        return airQualityIndex;
    }

    public void setAirQualityIndex(int airQualityIndex) {
        this.airQualityIndex = OptionalInt.of(airQualityIndex);
    }

    public Optional<String> getAirQualityText() {
        return airQualityText;
    }

    public void setAirQualityText(String airQualityText) {
        this.airQualityText = Optional.ofNullable(airQualityText);
    }

    public Optional<String> getAirQualityColor() {
        return airQualityColor;
    }

    public void setAirQualityColor(String airQualityColor) {
        this.airQualityColor = Optional.ofNullable(airQualityColor);
    }

}
