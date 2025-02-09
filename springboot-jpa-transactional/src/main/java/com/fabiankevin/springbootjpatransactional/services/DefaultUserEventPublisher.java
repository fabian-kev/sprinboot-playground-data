package com.fabiankevin.springbootjpatransactional.services;

import com.fabiankevin.springbootjpatransactional.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultUserEventPublisher implements UserEventPublisher {
    @Override
    public void publish(User user) {
        log.info("{} has been published.", user.getName());
    }
}
