package com.example.demo.Controller;

import com.example.demo.Model.WeatherResponse;
import com.example.demo.Constants.Constants;
import com.example.demo.Constants.Routes;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Controller
public class WeatherController {

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * @param model model to be sent to the view
     * @return view
     */
    @GetMapping(Routes.HOME)
    private String home(Model model) {
        /* Set default value of checkbox for air quality to false */
        model.addAttribute("airQualityCheckbox", false);

        return "home";
    }

    /**
     * @param city       indicates the city that the API will look for.
     * @param airQuality indicates whether the API will return air quality values.
     * @param model      model to be sent to the view.
     * @return view
     */
    @PostMapping(Routes.WHEATER)
    private String getWeather(@RequestParam String city,
            @RequestParam(name = "airQuality", required = false) boolean airQuality,
            Model model) {

        /* Initialize Gson object */
        Gson gson = new Gson();

        /* Initialize Dotenv object & get the API Key */
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("API_KEY");

        /* Check if air quality is being requested */
        String getAirQuality = Constants.NO;
        if (airQuality)
            getAirQuality = Constants.YES;

        /* Build URI to call Weather API */
        String uri = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=%s", apiKey, city,
                getAirQuality);

        /* Get response and parse it to Json */
        String responseJson = restTemplate.getForObject(uri, String.class);
        WeatherResponse weatherResponse = gson.fromJson(responseJson, WeatherResponse.class);

        /* Get temperature & icon */
        double temperature = weatherResponse.getCurrent().getTemp_c();
        String icon = weatherResponse.getCurrent().getCondition().getIcon();
        String weatherText = weatherResponse.getCurrent().getCondition().getText();

        /*
         * Send icon, city, temperature & whether air quality has been requested to html
         * view
         */
        model.addAttribute("icon", icon);
        model.addAttribute("weatherText", weatherText);
        model.addAttribute("city", StringUtils.capitalize(city));
        model.addAttribute("temperature", temperature);
        model.addAttribute("airQualityIncluded", airQuality);

        /* Check whether air quality has been requested */
        if (getAirQuality == Constants.YES) {
            /* Get index of air quality */
            int airQualityIndex = weatherResponse.getCurrent().getAir_quality().getUsEpaIndex();

            /* Return a string according to the index of air quality */
            String airQualityText = "";
            String airQualityColor = "";
            switch (airQualityIndex) {
                case 1:
                    airQualityText = Constants.GOOD_AQ;
                    airQualityColor = Constants.GOOD_AQ_COLOR;
                    break;
                case 2:
                    airQualityText = Constants.MODERATE_AQ;
                    airQualityColor = Constants.MODERATE_AQ_COLOR;
                    break;
                case 3:
                    airQualityText = Constants.UNHEALTHY_SG_AQ;
                    airQualityColor = Constants.UNHEALTHY_SG_AQ_COLOR;
                    break;
                case 4:
                    airQualityText = Constants.UNHEALTHY_AQ;
                    airQualityColor = Constants.UNHEALTHY_AQ_COLOR;
                    break;
                case 5:
                    airQualityText = Constants.VERY_UNHEALTHY_AQ;
                    airQualityColor = Constants.VERY_UNHEALTHY_AQ_COLOR;
                    break;
                case 6:
                    airQualityText = Constants.HAZARDOUS_AQ;
                    airQualityColor = Constants.HAZARDOUS_AQ_COLOR;
                    break;
            }

            /* Send air quality index & text to html view */
            model.addAttribute("airQuality", airQualityIndex);
            model.addAttribute("airQualityText", airQualityText);
            model.addAttribute("airQualityColor", airQualityColor);
        }

        return "weather";
    }
}
