package com.demo.weather.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeatherError {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String error;
    private String message;
    private String path;

    public WeatherError(HttpStatus status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
