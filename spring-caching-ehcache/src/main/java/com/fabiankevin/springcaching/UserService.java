package com.fabiankevin.springcaching;

import com.fabiankevin.springcaching.models.User;
import com.fabiankevin.springcaching.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@CacheConfig(cacheNames = {"users"})
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable
    public User retrieve(UUID id) {
        log.info("Retrieving user by id: {}", id);
        return userRepository.retrieve(id)
                .orElseThrow(() -> new RuntimeException("User does not exist."));
    }

    @Cacheable(key = "#user.id")
    public User create(User user) {
        log.info("Creating user {}", user.getEmail());
        return userRepository.create(user);
    }

    @CachePut(key = "#user.id")
    public User update(User user) {
        log.info("Updating user id={}, email={}", user.getId(), user.getEmail());
        User savedUser = retrieve(user.getId());

        User updatedUser = savedUser.toBuilder()
                .name(user.getName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();

        return userRepository.update(user.getId(), updatedUser);
    }

    @CacheEvict
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }


}
