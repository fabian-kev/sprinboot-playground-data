package com.fabiankevin.springbootjpatransactional.services;

import com.fabiankevin.springbootjpatransactional.models.User;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> retrieveAll();
}
