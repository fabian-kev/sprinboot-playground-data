package com.fabiankevin.springbootjpabatching.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "addresses")
@Entity(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String name;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}
