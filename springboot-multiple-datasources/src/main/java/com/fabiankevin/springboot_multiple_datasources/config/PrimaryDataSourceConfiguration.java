package com.fabiankevin.springboot_multiple_datasources.config;

import com.fabiankevin.springboot_multiple_datasources.persistence.first.WeatherEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(
        basePackageClasses = WeatherEntity.class,
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager")
public class PrimaryDataSourceConfiguration {

}