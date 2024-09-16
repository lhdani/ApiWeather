package com.demo.weather.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class WeatherException  extends RuntimeException {

    private HttpStatus status;

    public WeatherException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
