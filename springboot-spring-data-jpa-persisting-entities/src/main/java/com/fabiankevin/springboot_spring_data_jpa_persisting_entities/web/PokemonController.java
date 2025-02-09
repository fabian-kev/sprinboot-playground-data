package com.fabiankevin.springboot_spring_data_jpa_persisting_entities.web;

import com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.Pokemon;
import com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
@RequiredArgsConstructor
public class PokemonController {
    private final PokemonRepository pokemonRepository;


    @GetMapping("/type/{type}")
    private List<Pokemon> retrievePokemonByType(@PathVariable String type) {
        return pokemonRepository.findAllByType(type).stream().toList();
    }

}
