package com.fabiankevin.springboot_jpa_stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SpringbootJpaStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaStreamApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("HeapSize: {}", toMb(Runtime.getRuntime().totalMemory())+"MB");
            log.info("MaxHeapSize: {}", toMb(Runtime.getRuntime().maxMemory())+"MB");
            log.info("FreeHeapSize: {}", toMb(Runtime.getRuntime().freeMemory())+"MB");
        };
    }
    private static long toMb(Long bytes){
        return (bytes / 1024) / 1000;
    }
}
