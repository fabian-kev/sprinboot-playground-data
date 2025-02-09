package com.fabiankevin.springbootjpatransactional.web;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserRequest {
    String name;
    String email;
    LocalDate birthDate;
}
