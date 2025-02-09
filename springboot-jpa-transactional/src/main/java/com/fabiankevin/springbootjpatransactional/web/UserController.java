package com.fabiankevin.springbootjpatransactional.web;

import com.fabiankevin.springbootjpatransactional.models.User;
import com.fabiankevin.springbootjpatransactional.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserRequest userRequest) {
        User user = User.builder()
                .birthDate(userRequest.getBirthDate())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .build();
        User userResult = new User();
        try {
            userResult = userService.save(user);
        } catch (UnexpectedRollbackException e) {
            log.error("UnexpectedRollbackException {}", e.getMessage());
        }

        return userResult;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.retrieveAll();
    }
}
