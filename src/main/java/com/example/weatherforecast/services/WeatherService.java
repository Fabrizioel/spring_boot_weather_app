package com.example.weatherforecast.services;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

import com.example.weatherforecast.dtos.CurrentWeatherDTO;
import com.example.weatherforecast.enums.Constants;
import com.example.weatherforecast.models.current.CurrentWeatherResponse;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {
    
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final Dotenv dotenv;
    private final String apiKey;
    private final Random random;

    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder) {

        /* Initialize RestTemplate */
        this.restTemplate = restTemplateBuilder.build();
        
        /* Initialize Gson */
        this.gson = new Gson();

        /* Initialize Dotenv */
        this.dotenv = Dotenv.configure().load();

        /* Get API Key */
        this.apiKey = dotenv.get("API_KEY");

        this.random = new Random();
    }

    public CurrentWeatherDTO getCurrentWeather(String city, boolean airQuality) {

        System.out.println("entre");

        /* Check if air quality is being requested */
        String getAirQuality = Constants.NO;
        if (airQuality) getAirQuality = Constants.YES;

        /* Build URI to call Weather API */
        String uri = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=%s", apiKey, city, getAirQuality);

        /* Get response and parse it to Json */
        String responseJson = restTemplate.getForObject(uri, String.class);
        CurrentWeatherResponse weatherResponse = gson.fromJson(responseJson, CurrentWeatherResponse.class);

        /* Get temperature & icon */
        double temperature = random.nextInt(10) + 1;
        System.out.println(temperature);
        //double temperature = weatherResponse.getCurrent().getTemp_c();
        String icon = weatherResponse.getCurrent().getCondition().getIcon();
        String weatherText = weatherResponse.getCurrent().getCondition().getText();

        if (airQuality) {
            /* Get index of air quality */
            int airQualityIndex = weatherResponse.getCurrent().getAir_quality().getUsEpaIndex();

            /* Return air quality text & color according to the index of air quality */
            String airQualityText = "";
            String airQualityColor = switch (airQualityIndex) {
                case 1 -> {
                    airQualityText = Constants.GOOD_AQ;
                    yield Constants.GOOD_AQ_COLOR;
                }
                case 2 -> {
                    airQualityText = Constants.MODERATE_AQ;
                    yield Constants.MODERATE_AQ_COLOR;
                }
                case 3 -> {
                    airQualityText = Constants.UNHEALTHY_SG_AQ;
                    yield Constants.UNHEALTHY_SG_AQ_COLOR;
                }
                case 4 -> {
                    airQualityText = Constants.UNHEALTHY_AQ;
                    yield Constants.UNHEALTHY_AQ_COLOR;
                }
                case 5 -> {
                    airQualityText = Constants.VERY_UNHEALTHY_AQ;
                    yield Constants.VERY_UNHEALTHY_AQ_COLOR;
                }
                case 6 -> {
                    airQualityText = Constants.HAZARDOUS_AQ;
                    yield Constants.HAZARDOUS_AQ_COLOR;
                }
                default -> "";
            };

            return new CurrentWeatherDTO(icon, weatherText, city, temperature, airQuality, OptionalInt.of(airQualityIndex), Optional.of(airQualityText), Optional.of(airQualityColor));

        } else {

            return new CurrentWeatherDTO(icon, weatherText, city, temperature, airQuality, OptionalInt.empty(), Optional.empty(), Optional.empty());
            
        }

    }

    public void getForecast() {

    }
    
}
