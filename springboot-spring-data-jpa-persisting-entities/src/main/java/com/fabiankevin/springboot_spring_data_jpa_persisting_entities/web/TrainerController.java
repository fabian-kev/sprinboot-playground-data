package com.fabiankevin.springboot_spring_data_jpa_persisting_entities.web;

import com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.Pokemon;
import com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.Trainer;
import com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerRepository trainerRepository;

//    @GetMapping("/scrolling")
//    public List<Trainer> retrieveViaScrolling(){
////        WindowIterator<Trainer> users = WindowIterator.of(position -> trainerRepository.findAllByScrolling(position))
////                .startingAt(ScrollPosition.offset());
//
////        WindowIterator<Trainer> users = WindowIterator.of(position -> trainerRepository.findAllByScrolling( position))
////                .startingAt(ScrollPosition.keyset());
////
////        List<Trainer> trainers = new ArrayList<>();
////        while (users.hasNext()) {
////            trainers.add(users.next());
////            // consume the user
////        }
//        return trainers;
//    }

    @GetMapping("/top/{rank}")
    public List<Trainer> retrieveTopTrainers(@PathVariable int rank) {
        String name = "kevin";

        return trainerRepository.findTopTrainers(Pageable.ofSize(rank)).getContent();
    }

    @PostMapping
    public Trainer createTrainer(@RequestBody Trainer trainer) {
        Set<Trainer> trainers = IntStream.range(0, 200).boxed()
                .map(i -> {
                    return new Trainer(null, UUID.randomUUID().toString(), Collections.emptySet());
                }).collect(Collectors.toSet());
        trainerRepository.saveAllAndFlush(trainers);

        return trainerRepository.save(trainer);
    }

    @PostMapping("/{id}/pokemon")
    public Trainer addPokemon(@RequestBody Pokemon pokemon, @PathVariable UUID id) {
        Trainer trainer = trainerRepository.findById(id).get();
        trainer.addPokemon(pokemon);
        return trainerRepository.save(trainer);
    }

    @PostMapping("/add-all")
    public void addAll() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Pikachu");
        pokemon.setType("Electric");

        Set<Trainer> trainerWithPikachu = trainerRepository.findAll().stream()
                .peek(trainer -> trainer.addPokemon(pokemon))
                .collect(Collectors.toSet());

//        trainerRepository.saveAllAndFlush(trainerWithPikachu);
    }

}
