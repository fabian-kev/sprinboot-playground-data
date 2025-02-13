package com.fabiankevin.springboot_multiple_datasources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.fabiankevin.springboot_multiple_datasources.persistence.primary")
public class SpringbootMultipleDatasourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultipleDatasourcesApplication.class, args);
    }

}
