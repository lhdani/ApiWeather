package com.demo.weather.service;

import com.demo.weather.configuration.WeatherConfig;
import com.demo.weather.exception.WeatherException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.demo.weather.model.Weather;
import com.demo.weather.repository.WeatherRepository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    private static final String ERROR_INESPERADO = "Error inesperado al consultar AccuWeather API: ";

    private static final String ERROR_CONSULTA = "Error al consultar AccuWeather API: ";

    public Weather fetchAndSaveWeather(String locationKey) {
        String apiKey = weatherConfig.getApikey();
        String url = weatherConfig.getUrlCurrentConditions() + locationKey + "?apikey=" + apiKey;
        try {
            Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);

            if (response != null && response.length > 0) {
                Map<String, Object> weatherData = response[0];

                String weatherDescription = (String) weatherData.get("WeatherText");

                Map<String, Object> temperatureMap = (Map<String, Object>) weatherData.get("Temperature");
                Map<String, Object> metricMap = (Map<String, Object>) temperatureMap.get("Metric");
                Map<String, Object> imperialMap = (Map<String, Object>) temperatureMap.get("Imperial");

                double temperatureCelsius = (Double) metricMap.get("Value");
                double temperatureFahrenheit = (Double) imperialMap.get("Value");

                Weather weather = new Weather();
                weather.setLocation(locationKey);
                weather.setWeatherDescription(weatherDescription);
                weather.setTemperatureCelsius(temperatureCelsius);
                weather.setTemperatureFahrenheit(temperatureFahrenheit);

                return weatherRepository.save(weather);
            } else {
                throw new WeatherException("No se encontró información del clima para la clave de ubicación: "
                        + locationKey, HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            throw new WeatherException(ERROR_CONSULTA + e.getStatusCode() + " "
                    + e.getStatusText(), (HttpStatus) e.getStatusCode());
        } catch (Exception e) {
            throw new WeatherException(ERROR_INESPERADO + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public String getLocationKey(String cityName) {
        String apiKey = weatherConfig.getApikey();
        String url = weatherConfig.getUrlLocations() + cityName + "&apikey=" + apiKey;
        try {
            Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);

            if (response != null && response.length > 0) {
                Map<String, Object> locationData = response[0];
                return (String) locationData.get("Key");
            } else {
                throw new WeatherException("No se encontró el location key para la ciudad: "
                        + cityName, HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            throw new WeatherException(ERROR_CONSULTA + e.getStatusCode() + " "
                    + e.getStatusText(), (HttpStatus) e.getStatusCode());
        } catch (Exception e) {
            throw new WeatherException(ERROR_INESPERADO + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
