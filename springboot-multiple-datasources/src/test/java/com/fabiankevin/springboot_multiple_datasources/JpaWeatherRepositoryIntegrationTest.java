package com.fabiankevin.springboot_multiple_datasources;

import com.fabiankevin.springboot_multiple_datasources.persistence.primary.JpaWeatherRepository;
import com.fabiankevin.springboot_multiple_datasources.persistence.primary.WeatherEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaWeatherRepositoryIntegrationTest {
    @Autowired
    private JpaWeatherRepository jpaWeatherRepository;

    @Test
    void save_givenValidEntity_thenShouldSucceed() {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setName("Sunny");
        jpaWeatherRepository.save(weatherEntity);
    }

}
