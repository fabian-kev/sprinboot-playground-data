package com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.wrapper;

import com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.Pokemon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;

import java.util.Iterator;

//wrapper of Pokemons using streamable
@RequiredArgsConstructor(staticName = "of")
public class Pokemons implements Streamable<Pokemon> {
    private final Streamable<Pokemon> pokemonStreamable;

    @Override
    public Iterator<Pokemon> iterator() {
        return pokemonStreamable.iterator();
    }
}
