package com.demo.weather.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("weather")
@Getter
@Setter
public class WeatherConfig {

    private String apikey;
    private String urlCurrentConditions;
    private String urlLocations;
}
