package com.fabiankevin.springboot_multiple_datasources.schedulers;

import com.fabiankevin.springboot_multiple_datasources.persistence.first.JpaWeatherRepository;
import com.fabiankevin.springboot_multiple_datasources.persistence.first.WeatherEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class WeatherScheduler {
    private final JpaWeatherRepository jpaWeatherRepository;
    private boolean initial = true;

    @Scheduled(fixedDelay = 5000L)
    public void createRandomWeather() {
        if (initial) {
            int size = jpaWeatherRepository.findAll().size();
            log.info("Weather system has started with initial weathers count of {}", size);
            initial = false;
        }

        WeatherEntity entity = new WeatherEntity();
        entity.setName(UUID.randomUUID().toString());
        entity.setCreatedDate(Instant.now());
        jpaWeatherRepository.save(entity);

        log.info("Weather with name: {} has been created.", entity.getName());
    }
}
