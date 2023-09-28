package com.example.weatherforecast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.weatherforecast.beans.ForecastScheduler;
import com.example.weatherforecast.dtos.CurrentWeatherDTO;
import com.example.weatherforecast.enums.Routes;
import com.example.weatherforecast.services.WeatherService;

import org.springframework.boot.web.client.RestTemplateBuilder;

@Controller
public class WeatherController {

    private final ForecastScheduler forecastScheduler;
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(RestTemplateBuilder restTemplateBuilder, ForecastScheduler forecastScheduler, WeatherService weatherService) {
        
        /* Initialize ForecastScheduler */
        this.forecastScheduler = forecastScheduler;

        /* Initialize WeatherService */
        this.weatherService = weatherService;
    }

    /**
     * @param model model to be sent to the view
     * @return view
     */
    @GetMapping(Routes.HOME)
    private String home(Model model) {

        /* Stop cron task */
        forecastScheduler.toggleTaskExecution(false);

        /* Set default value of checkbox for air quality to false */
        model.addAttribute("airQualityCheckbox", false);

        return "home";
    }

    /**
     * @param city       indicates the city that the API will look for
     * @param airQuality indicates whether the API will return air quality values
     * @param model      model to be sent to the view
     * @return view
     */
    @PostMapping(Routes.WHEATER)
    private String getWeather(@RequestParam String city,
            @RequestParam(name = "airQuality", required = false) boolean airQuality,
            Model model) {
        
        /* Stop cron task */
        forecastScheduler.toggleTaskExecution(true);

        forecastScheduler.setParameters(city, airQuality);

        CurrentWeatherDTO currentWeatherDTO = weatherService.getCurrentWeather(city, airQuality);

        /* Send icon, city, temperature & whether air quality has been requested to html view */
        model.addAttribute("icon", currentWeatherDTO.getIcon());
        model.addAttribute("weatherText", currentWeatherDTO.getWeatherText());
        model.addAttribute("city", StringUtils.capitalize(currentWeatherDTO.getCity()));
        model.addAttribute("temperature", currentWeatherDTO.getTemperature());
        model.addAttribute("airQualityIncluded", currentWeatherDTO.getAirQuality());

        if (airQuality) {
            /* Send air quality index, text & color to html view */
            model.addAttribute("airQuality", currentWeatherDTO.getAirQualityIndex().getAsInt());
            model.addAttribute("airQualityText", currentWeatherDTO.getAirQualityText().get());
            model.addAttribute("airQualityColor", currentWeatherDTO.getAirQualityColor().get());
        }

        return "weather";
    }

    /**
     * @param city  indicates the city that the API will look for
     * @param model model to be sent to the view
     * @return view
     */
    @GetMapping(Routes.FORECAST)
    private String getForecast(Model model) {
        
        /* Run cron task */
        forecastScheduler.toggleTaskExecution(true);

        return "forecast";
    }
}
