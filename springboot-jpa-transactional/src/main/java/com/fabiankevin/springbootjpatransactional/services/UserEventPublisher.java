package com.fabiankevin.springbootjpatransactional.services;

import com.fabiankevin.springbootjpatransactional.models.User;

public interface UserEventPublisher {
    void publish(User user);
}
