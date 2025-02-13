package com.fabiankevin.springboot_multiple_datasources;

import com.fabiankevin.springboot_multiple_datasources.persistence.secondary.JpaMovieRepository;
import com.fabiankevin.springboot_multiple_datasources.persistence.secondary.MovieEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JpaMovieRepositoryTest {
    // it uses the secondary datasource
    @Autowired
    private JpaMovieRepository jpaMovieRepository;

    @Test
    void save_givenValidEntity_thenShouldSucceed(){
        MovieEntity entity = new MovieEntity();
        entity.setName("Avatar");
        MovieEntity savedEntity = jpaMovieRepository.save(entity);

        assertNotNull(savedEntity.getId(), "id");
        assertEquals("Avatar", savedEntity.getName(), "name");
    }
}
