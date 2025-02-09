package com.fabiankevin.springbootjpabatching.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Table(name = "students")
@Entity(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class StudentEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String name;
    private Integer age;
    private String status;
    private Instant createdDate;
}
