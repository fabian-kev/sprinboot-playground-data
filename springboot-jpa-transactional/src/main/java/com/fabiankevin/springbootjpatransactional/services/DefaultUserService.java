package com.fabiankevin.springbootjpatransactional.services;

import com.fabiankevin.springbootjpatransactional.models.User;
import com.fabiankevin.springbootjpatransactional.persistence.JpaUserRepository;
import com.fabiankevin.springbootjpatransactional.persistence.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {
    private final JpaUserRepository jpaUserRepository;
    private final UserEventPublisher userEventPublisher;

    @Override
    @Transactional(dontRollbackOn = {ConstraintViolationException.class})
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setBirthDate(user.getBirthDate());
        UserEntity savedUser = jpaUserRepository.saveAndFlush(entity);
        try {
//            userEventPublisher.publish(user);
            jpaUserRepository.saveAndFlush(entity.toBuilder().id(null).build());
        } catch (RuntimeException e) {
            log.error("encountered an error {} {}", e.getMessage(), e.getCause().toString());
        }

        return toUser(savedUser);
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
