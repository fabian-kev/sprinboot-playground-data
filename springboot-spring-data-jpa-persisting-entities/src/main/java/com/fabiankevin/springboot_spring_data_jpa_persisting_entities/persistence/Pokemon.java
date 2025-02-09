package com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pokemons")
@ToString(exclude = "trainer")
@EqualsAndHashCode(exclude = "trainer")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String type;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @JsonIgnore
    private Trainer trainer;


}
