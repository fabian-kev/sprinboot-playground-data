package com.fabiankevin.springbootjpatransactional.services;

import com.fabiankevin.springbootjpatransactional.models.User;
import com.fabiankevin.springbootjpatransactional.persistence.JpaUserRepository;
import com.fabiankevin.springbootjpatransactional.persistence.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DefaultUserService implements UserService {
    private final JpaUserRepository jpaUserRepository;
    private final UserEventPublisher userEventPublisher;

    @Override
    public User save(CreateUserCommand command) {
        UserEntity entity = new UserEntity();
        entity.setName(command.getName());
        entity.setEmail(command.getEmail());
        entity.setBirthDate(command.getBirthDate());
        UserEntity savedUser = jpaUserRepository.save(entity);
        User user = toUser(savedUser);
        userEventPublisher.publish(user);

        return user;
    }

    @Override
    public List<User> retrieveAll() {
        return jpaUserRepository.findAll().stream()
                .map(DefaultUserService::toUser)
                .toList();
    }

    private static User toUser(UserEntity savedUser) {
        return User.builder()
                .id(savedUser.getId())
                .birthDate(savedUser.getBirthDate())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
    }
}
