package com.example.weatherforecast.beans;

import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.weatherforecast.dtos.CurrentWeatherDTO;
import com.example.weatherforecast.services.WeatherService;

@Component
public class ForecastScheduler {

    private String city;
    private boolean airQuality;
    private boolean runForecastTask = false;
    
    @Autowired
    private WeatherService weatherService;
    
    public void setParameters(String city, boolean airQuality) {
        this.city = city;
        this.airQuality = airQuality;
    }

    @Scheduled(cron = "${com.scheduled.cron}")
    public CurrentWeatherDTO runForecastTask() {
        if (runForecastTask) {
            return weatherService.getCurrentWeather(city, airQuality);
        }
        return new CurrentWeatherDTO("", "", "", 0.0,false,OptionalInt.empty(),Optional.empty(),Optional.empty());
    }

    public void toggleTaskExecution(boolean flag) {
        runForecastTask = flag;
    }
}
