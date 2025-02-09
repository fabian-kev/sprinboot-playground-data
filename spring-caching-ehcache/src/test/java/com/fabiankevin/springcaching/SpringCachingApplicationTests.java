package com.fabiankevin.springcaching;

import com.fabiankevin.springcaching.models.User;
import com.fabiankevin.springcaching.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringCachingApplicationTests {
    @MockitoBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void retrieve_givenRetrieveHasInvokedTwiceWithSameId_thenRepositoryShouldCallOnce() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .name("test")
                .id(userId)
                .email("test@test.com")
                .birthDate(LocalDate.now())
                .build();
        when(userRepository.retrieve(userId)).thenReturn(Optional.ofNullable(User.builder()
                .name("test")
                .id(userId)
                .email("test@test.com")
                .birthDate(LocalDate.now())
                .build()));

        userService.retrieve(userId);
        User retrievedUser = userService.retrieve(userId);
        assertEquals(user, retrievedUser, "should be equal");

        verify(userRepository, times(1)).retrieve(userId);
    }

    @Test
    void create_givenRetrieveHasInvokedTwiceWithSameId_thenRepositoryShouldCallOnce() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .name("test")
                .id(userId)
                .email("test@test.com")
                .birthDate(LocalDate.now())
                .build();
        when(userRepository.create(any())).thenReturn(user);

        userService.create(user);
        User retrievedUser = userService.retrieve(userId);

        assertEquals(user, retrievedUser, "should be equal");

        verify(userRepository, times(1)).create(any());
        verify(userRepository, times(0)).retrieve(userId);
    }

    @Test
    void update_givenUserHasBeenUpdated_thenCacheShouldBeUpdated() {
        UUID userId = UUID.randomUUID();
        User oldUser = User.builder()
                .name("old")
                .id(userId)
                .email("old@test.com")
                .birthDate(LocalDate.now())
                .build();

        User updatedUser = User.builder()
                .name("test")
                .id(userId)
                .email("test@test.com")
                .birthDate(LocalDate.now())
                .build();

        // To register to the cache
        when(userRepository.retrieve(userId)).thenReturn(Optional.ofNullable(oldUser));
        userService.retrieve(userId);

        when(userRepository.update(userId, updatedUser)).thenReturn(updatedUser);
        userService.update(updatedUser);

        User retrievedUser = userService.retrieve(userId);

        assertEquals(updatedUser, retrievedUser, "should be equal");
        verify(userRepository, times(2)).retrieve(userId);
    }

    @Test
    void deleteById_givenExistingUser_thenUserShouldThrowUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .name("test")
                .id(userId)
                .email("test@test.com")
                .birthDate(LocalDate.now())
                .build();

        when(userRepository.create(any())).thenReturn(user);

        userService.deleteById(userId);
        assertThrows(RuntimeException.class,
                () -> userService.retrieve(userId),
                "expecting runtime exception due to user does not exist");

        verify(userRepository, times(1)).retrieve(userId);
    }
}
