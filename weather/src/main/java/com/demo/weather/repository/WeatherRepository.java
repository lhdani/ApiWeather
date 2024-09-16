package com.demo.weather.repository;

import com.demo.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
