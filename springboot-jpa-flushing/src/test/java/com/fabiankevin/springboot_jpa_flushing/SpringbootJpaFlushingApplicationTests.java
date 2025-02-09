package com.fabiankevin.springboot_jpa_flushing;

import com.fabiankevin.springboot_jpa_flushing.models.CreateUserCommand;
import com.fabiankevin.springboot_jpa_flushing.persistence.UserEntity;
import com.fabiankevin.springboot_jpa_flushing.persistence.UserRepository;
import com.fabiankevin.springboot_jpa_flushing.services.EventPublisher;
import com.fabiankevin.springboot_jpa_flushing.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@SpringBootTest
class SpringbootJpaFlushingApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @MockitoBean
    private EventPublisher publisher;

    @Test
    void createUserWithFlush_givenEncounteredNameConstraint_thenShouldNotPublish() {
        CreateUserCommand command = new CreateUserCommand();
        command.setName("Sandro");
        command.setBirthDate(LocalDate.now().minusYears(6));

        userRepository.save(UserEntity.builder()
                .birthDate(LocalDate.now())
                .name("Sandro")
                .build());

        assertThrows(DataIntegrityViolationException.class,
                () -> userService.createUserWithFlush(command));

        verifyNoInteractions(publisher);
    }

    @Test
        // This test fails because it uses jpa.save which flush and commit at the end of a transaction.
    void createUser_givenEncounteredNameConstraint_thenShouldNotPublish() {
        CreateUserCommand command = new CreateUserCommand();
        command.setName("Sandro");
        command.setBirthDate(LocalDate.now().minusYears(6));

        userRepository.save(UserEntity.builder()
                .birthDate(LocalDate.now())
                .name("Sandro")
                .build());

        assertThrows(DataIntegrityViolationException.class,
                () -> userService.createUser(command));

        verifyNoInteractions(publisher);
    }

}
