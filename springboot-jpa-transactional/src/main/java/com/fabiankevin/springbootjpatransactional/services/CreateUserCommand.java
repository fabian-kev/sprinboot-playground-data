package com.fabiankevin.springbootjpatransactional.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserCommand {
    String name;
    String email;
    LocalDate birthDate;
}
