package com.fabiankevin.springboot_jpa_flushing.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserCommand {
    private String name;
    private LocalDate birthDate;
}
