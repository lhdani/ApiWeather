package com.demo.weather.exception;

import com.demo.weather.model.WeatherError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WeatherExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<WeatherError> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                   HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        WeatherError apiError = new WeatherError(
                HttpStatus.BAD_REQUEST,
                "Validación Fallida",
                "Se encontraron errores en los datos de entrada.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WeatherException.class)
    @ResponseBody
    public ResponseEntity<WeatherError> handleApiException(WeatherException ex, HttpServletRequest request) {
        WeatherError apiError = new WeatherError(
                ex.getStatus(),
                "Error en la API de AccuWeather",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<WeatherError> handleGlobalException(Exception ex, HttpServletRequest request) {
        WeatherError apiError = new WeatherError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error Interno del Servidor",
                "Ocurrió un error inesperado.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
