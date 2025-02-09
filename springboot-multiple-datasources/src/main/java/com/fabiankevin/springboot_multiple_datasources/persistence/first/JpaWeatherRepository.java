package com.fabiankevin.springboot_multiple_datasources.persistence.first;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaWeatherRepository extends JpaRepository<WeatherEntity, UUID> {
}
