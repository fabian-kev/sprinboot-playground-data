package com.fabiankevin.springboot_jpa_flushing.services;

import com.fabiankevin.springboot_jpa_flushing.models.CreateUserCommand;
import com.fabiankevin.springboot_jpa_flushing.models.User;
import com.fabiankevin.springboot_jpa_flushing.persistence.UserEntity;
import com.fabiankevin.springboot_jpa_flushing.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public User createUser(CreateUserCommand comamnd) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(comamnd.getName());
        userEntity.setBirthDate(comamnd.getBirthDate());

        UserEntity savedUser = userRepository.save(userEntity);
        User user = new User();
        user.setId(savedUser.getId());
        user.setName(savedUser.getName());
        user.setBirthDate(savedUser.getBirthDate());

        eventPublisher.publish(user);

        return user;
    }

    @Transactional
    public User createUserWithFlush(CreateUserCommand comamnd) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(comamnd.getName());
        userEntity.setBirthDate(comamnd.getBirthDate());

        UserEntity savedUser = userRepository.saveAndFlush(userEntity);
        User user = new User();
        user.setId(savedUser.getId());
        user.setName(savedUser.getName());
        user.setBirthDate(savedUser.getBirthDate());

        eventPublisher.publish(user);

        return user;
    }
}
