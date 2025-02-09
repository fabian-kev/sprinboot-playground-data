package com.fabiankevin.springcaching.repositories;

import com.fabiankevin.springcaching.models.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();


    public Optional<User> retrieve(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public User create(User user) {
        User updatedUser = user.toBuilder()
                .id(UUID.randomUUID())
                .build();
        users.put(updatedUser.getId(), updatedUser);
        return updatedUser;
    }

    public User update(UUID id, User user) {
        users.put(id, user);
        return user;
    }

    public void deleteById(UUID id) {
        users.remove(id);
    }
}
