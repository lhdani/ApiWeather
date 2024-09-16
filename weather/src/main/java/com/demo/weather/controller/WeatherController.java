package com.demo.weather.controller;

import com.demo.weather.model.Weather;
import com.demo.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
@Tag(name = "Weather API", description = "API para consultar el clima")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/{locationKey}")
    @Operation(summary = "Obtiene la información del clima para una ubicación específica",
            description = "Proporciona la temperatura actual y la descripción del clima.")
    public Weather getWeather(@Parameter(description = "Clave de la ubicación proporcionada por AccuWeather", required = true)
                                  @NotBlank(message = "La clave de ubicación no puede estar vacía")
                                  @PathVariable String locationKey) {
        return weatherService.fetchAndSaveWeather(locationKey);
    }

    @GetMapping("/locationKey/{cityName}")
    @Operation(summary = "Obtiene el location key para una ciudad específica",
            description = "Proporciona el location key que se puede usar para obtener información del clima.")
    public String getLocationKey(
            @Parameter(description = "Nombre de la ciudad para buscar el location key", required = true)
            @NotBlank(message = "El nombre de la ciudad no puede estar vacío")
            @Size(min = 2, max = 100, message = "El nombre de la ciudad debe tener entre 2 y 100 caracteres")
            @PathVariable String cityName) {
        return weatherService.getLocationKey(cityName);
    }

}
