package com.fabiankevin.springbootjpaintegrationtest.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaGemRepository extends JpaRepository<GemEntity, Long> {

    @Query("SELECT g FROM gems g WHERE g.id=:id and g.color=:color")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<GemEntity> findAndLockById(Long id, String color);

    @Query(value = "SELECT g.id, g.rrn, g.name, h.owner FROM gems g LEFT JOIN histories h ON g.id = h.gem_id", nativeQuery = true)
    List<Object[]> findAllGemObjects(PageRequest pageRequest);


    @Query("SELECT new com.fabiankevin.springbootjpaintegrationtest.persistence.FlatGemDto(g.id, g.rrn, g.name, g.color, g.clarity, g.cut, h.transferredDate, g.prices, h.owner) FROM gems g LEFT JOIN g.histories h")
    List<FlatGemDto> findFlatGems();

    default List<GemFlatDto> findGems(PageRequest request) {
        List<Object[]> results = findAllGemObjects(request);
        return results.stream()
                .map(result -> {
                    UUID rrn = Optional.ofNullable(result[1])
                            .map(objectRrn -> {
                                return UUID.nameUUIDFromBytes(objectRrn.toString().getBytes(StandardCharsets.UTF_8));
                            })
                            .orElse(null);
//                    String stringRRN = (String) result[1];
//                    UUID rrn = UUID.fromString(stringRRN);
                    String owner = (String) result[3];
                    return new GemFlatDto((Long) result[0], rrn, (String) result[2], owner);
                })
                .toList();
    }
}
