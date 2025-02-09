package com.fabiankevin.springbootjpaintegrationtest;

import com.fabiankevin.springbootjpaintegrationtest.persistence.FlatGemDto;
import com.fabiankevin.springbootjpaintegrationtest.persistence.GemEntity;
import com.fabiankevin.springbootjpaintegrationtest.persistence.HistoryEntity;
import com.fabiankevin.springbootjpaintegrationtest.persistence.JpaGemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class JpaGemRepositoryTest {

    @Autowired
    private JpaGemRepository jpaGemRepository;

    @Test
    void findFlatGems_() {
        Set<GemEntity> gemEntities = IntStream.range(0, 10).boxed()
                .map(i -> {
                    GemEntity gemEntity = new GemEntity();
                    gemEntity.setClarity("high");
                    gemEntity.setCut("diamond");
                    gemEntity.setRrn(UUID.randomUUID());
                    gemEntity.setName(UUID.randomUUID().toString());

                    HistoryEntity historyEntity = new HistoryEntity();
                    historyEntity.setOwner("Kevin");
                    gemEntity.addHistory(historyEntity);
                    return gemEntity;
                }).collect(Collectors.toSet());
        jpaGemRepository.saveAll(gemEntities);

        List<FlatGemDto> flatGems = jpaGemRepository.findFlatGems();
        for (FlatGemDto flatGem : flatGems) {
            assertNotNull(flatGem.getId());
            assertNotNull(flatGem.getRrn());
            assertNotNull(flatGem.getName());
            assertEquals("Kevin", flatGem.getOwner());
        }

    }
}
