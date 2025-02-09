package com.fabiankevin.springbootjpabatching.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "students")
@Entity(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(exclude = {"addresses"})
@ToString(exclude = {"addresses"})
public class StudentEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String name;
    private Integer age;
    private String status;
    private Instant createdDate;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "student")
    private Set<AddressEntity> addresses = new HashSet<>();

    public void addAddress(AddressEntity addressEntity) {
        addressEntity.setStudent(this);
        this.addresses.add(addressEntity);
    }
}
