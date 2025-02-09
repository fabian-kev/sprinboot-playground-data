package com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trainers")
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Pokemon> pokemonSet = new HashSet<>();

    public void addPokemon(final Pokemon pokemon) {
        pokemon.setTrainer(this);
        pokemonSet.add(pokemon);
    }
}
