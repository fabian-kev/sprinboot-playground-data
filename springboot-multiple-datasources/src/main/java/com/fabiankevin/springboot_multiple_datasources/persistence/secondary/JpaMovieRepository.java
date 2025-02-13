package com.fabiankevin.springboot_multiple_datasources.persistence.secondary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMovieRepository extends JpaRepository<MovieEntity, UUID> {
}
