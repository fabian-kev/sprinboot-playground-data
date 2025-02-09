package com.fabiankevin.springbootjpatransactional;

import com.fabiankevin.springbootjpatransactional.exceptions.EventPublishingException;
import com.fabiankevin.springbootjpatransactional.models.User;
import com.fabiankevin.springbootjpatransactional.persistence.JpaUserRepository;
import com.fabiankevin.springbootjpatransactional.services.CreateUserCommand;
import com.fabiankevin.springbootjpatransactional.services.UserEventPublisher;
import com.fabiankevin.springbootjpatransactional.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class SpringbootJpaTransactionalApplicationTests {
    @Autowired
    private UserService userService;
    @MockitoBean
    private UserEventPublisher userEventPublisher;
    @Autowired
    private JpaUserRepository jpaUserRepository;

    @BeforeEach
    public void beforeEach(){
        jpaUserRepository.deleteAll();
    }

    @Test
    void createUser_givenValidCommand_thenShouldBeCreated() {
        CreateUserCommand command = new CreateUserCommand();
        command.setName("Sandro");
        command.setEmail("sandro@test.com");
        command.setBirthDate(LocalDate.now().minusYears(10));

        User savedUser = userService.save(command);
        assertEquals("Sandro", savedUser.getName());
        assertEquals("sandro@test.com", savedUser.getEmail());
        assertEquals(LocalDate.now().minusYears(10), savedUser.getBirthDate());
    }

    @Test
    void createUser_givenPublisherThrowsException_thenShouldRollback() {
        CreateUserCommand command = new CreateUserCommand();
        command.setName("Sandro");
        command.setEmail("sandro@test.com");
        command.setBirthDate(LocalDate.now().minusYears(10));
        doThrow(EventPublishingException.class)
                .when(userEventPublisher)
                        .publish(any());
        assertThrows(EventPublishingException.class,
                () -> userService.save(command));

        long count = jpaUserRepository.count();
        assertEquals(0, count, "Expecting no user created because it has been rollback");
    }
}
