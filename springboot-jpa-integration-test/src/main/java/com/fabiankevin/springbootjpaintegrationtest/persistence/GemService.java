package com.fabiankevin.springbootjpaintegrationtest.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class GemService {

    private final JpaGemRepository jpaGemRepository;

    @Transactional
    public void publishGem() {
        log.info("retrieving inactive gem");
        GemEntity gemEntity = jpaGemRepository.findAndLockById(1l, "RED")
                .orElse(null);
        log.info("Retrieved gem: {}", gemEntity);
        gemEntity.setColor("ORANGE");
        GemEntity updatedGem = jpaGemRepository.saveAndFlush(gemEntity);
        log.info("Updated Gem: {}", updatedGem);
    }
}
