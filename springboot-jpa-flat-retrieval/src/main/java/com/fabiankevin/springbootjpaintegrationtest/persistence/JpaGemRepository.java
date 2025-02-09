package com.fabiankevin.springbootjpaintegrationtest.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaGemRepository extends JpaRepository<GemEntity, Long> {
    @Query("SELECT new com.fabiankevin.springbootjpaintegrationtest.persistence.FlatGemDto(g.id, g.rrn, g.name, h.owner) FROM gems g JOIN g.histories h")
    List<FlatGemDto> findFlatGems();

}
