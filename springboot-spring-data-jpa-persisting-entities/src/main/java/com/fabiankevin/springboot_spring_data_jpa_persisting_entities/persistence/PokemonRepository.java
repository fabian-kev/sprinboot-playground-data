package com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence;

import com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.wrapper.Pokemons;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, UUID> {
    //    This is useful on project architecture that uses multiple models, for example entity to business model. We can transform it
//    Streamable<Pokemon> findAllByType(String type);
    @EntityGraph(attributePaths = "trainer")
    Pokemons findAllByType(String type);
}
