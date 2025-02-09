package com.fabiankevin.springboot_jpa_flushing.services;

import com.fabiankevin.springboot_jpa_flushing.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventPublisher {

    public void publish(User user) {
        log.info("{} has been published.");
    }
}
