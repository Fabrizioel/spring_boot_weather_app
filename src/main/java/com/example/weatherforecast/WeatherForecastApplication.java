package com.example.weatherforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WeatherForecastApplication {
	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastApplication.class, args);
	}
}
