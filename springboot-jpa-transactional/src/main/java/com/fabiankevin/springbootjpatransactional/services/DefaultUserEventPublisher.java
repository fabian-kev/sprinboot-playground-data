package com.fabiankevin.springbootjpatransactional.services;

import com.fabiankevin.springbootjpatransactional.exceptions.EventPublishingException;
import com.fabiankevin.springbootjpatransactional.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class DefaultUserEventPublisher implements UserEventPublisher {
    @Override
    public void publish(User user) {
        if (user.getName().startsWith("test")) {
            throw new EventPublishingException();
        }
        log.info("{} has been published.", user.getName());
    }
}
