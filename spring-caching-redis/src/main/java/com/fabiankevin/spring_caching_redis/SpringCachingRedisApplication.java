package com.fabiankevin.spring_caching_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class SpringCachingRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCachingRedisApplication.class, args);
    }

}
