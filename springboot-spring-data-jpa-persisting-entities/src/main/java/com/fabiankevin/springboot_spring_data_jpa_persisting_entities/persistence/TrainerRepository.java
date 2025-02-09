package com.fabiankevin.springboot_spring_data_jpa_persisting_entities.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, UUID> {

    @EntityGraph(attributePaths = "pokemonSet")
    @Meta(comment = "Find all available trainers and eagerly retrieve child collections.")
    List<Trainer> findAll();

//    @Query("SELECT t FROM trainers t")
//    @EntityGraph(attributePaths = "pokemonSet")
//    Window<Trainer> findAllByScrolling(KeysetScrollPosition   position);


    @Query("SELECT t FROM trainers t")
    @EntityGraph(attributePaths = "pokemonSet")
//    This still produces the same error as simple pagination.
//    You will encounter the error below if you use pagination with entity that has collection in or if it has one to many relationship.
//    For example, if you have 1m entries in the database, Hibernate will load all of that in a memory and do the pagination in application level not in database level.
//    HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
    Slice<Trainer> findTopTrainers(Pageable pageable);


}
