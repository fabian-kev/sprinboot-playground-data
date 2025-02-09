package com.fabiankevin.springbootjpaintegrationtest.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles({"test"})
@Slf4j
@Import({JpaGemRepositoryIngerationbTest.SampleConfig.class})
//@Tag("integ")
public class JpaGemRepositoryIngerationbTest {

    @Autowired
    private JpaGemRepository jpaGemRepository;

    @Autowired
    private GemService gemService;

    @TestConfiguration
    public static class SampleConfig {
        @Bean
        public GemService gemService(JpaGemRepository jpaGemRepository) {
            return new GemService(jpaGemRepository);
        }
    }

    CountDownLatch latch;
    ExecutorService executorService;

    @BeforeEach
    void setup() throws InterruptedException {
        latch = new CountDownLatch(1);
        executorService = Executors.newFixedThreadPool(3);
        ;
        latch.await(1000, TimeUnit.MILLISECONDS);
    }

    @Test
    void givenPessimisticWriteLock_thenOnlyOneRetrieveShouldSUcceed() throws ExecutionException, InterruptedException {
        Callable<Void> c1 = () -> {
            gemService.publishGem();
            return null;
        };

        Callable<Void> c2 = () -> {
            gemService.publishGem();
            return null;
        };

        List<Callable<Void>> c11 = List.of(c1, c2);
        List<Future<Void>> futures = executorService.invokeAll(c11);
        for (Future<Void> future : futures) {
            future.get();
        }
    }


    @Test
    void getFlatGems() {
        List<FlatGemDto> flatGems = jpaGemRepository.findFlatGems();
        ObjectToSetAttributeConverter objectToSetAttributeConverter = new ObjectToSetAttributeConverter();
        for (FlatGemDto flatGem : flatGems) {
//            setJsonAttributeConverter.convertToEntityAttribute(String.valueOf(flatGem.getPrices()))
            log.info("Id: {}", flatGem.getId());
            log.info("Rrn: {}", flatGem.getRrn());
            log.info("Name: {}", flatGem.getName());
            log.info("Owner: {}", flatGem.getOwner());
            log.info("Transferred Date: {}", flatGem.getTransferredDate());
            Set<Amount> amounts = objectToSetAttributeConverter.convertToEntityAttribute(flatGem.getPrices());
//            log.info("Prices {}", amounts.get(0).getCurrency());
//            log.info("Prices {}", amounts.get(0).getValue());
            for (Amount amount : amounts) {
                log.info(" - Value: {}", amount.getValue());
                log.info(" - Currency: {}", amount.getCurrency());
            }
        }

        Collector<FlatGemDto, ?, List<HistoryEntity>> mapping = Collectors.mapping(flatGemDto -> {
            HistoryEntity history = new HistoryEntity();
            history.setOwner(flatGemDto.getOwner());
            return history;
        }, Collectors.toList());

        Map<GemEntity, List<HistoryEntity>> collect = flatGems.stream()
                .collect(Collectors.groupingBy(flatGemDto -> {
                    GemEntity gemEntity = new GemEntity();
                    gemEntity.setId(flatGemDto.getId());
                    gemEntity.setName(flatGemDto.getName());
                    return gemEntity;
                }, mapping));

//        collect.entrySet().stream()
//                .map(gemEntityListEntry -> {
//                    gemEntityListEntry.get
//                })
//        collect.forEach((gemEntity, historyEntities) -> {
//            gemEntity.setHistories(new HashSet<>(historyEntities));
//        });
//
//        log.info(gemE);

//        collect.forEach((aLong, flatGemDtos) -> );


//        List<GemFlatDto> allGems = jpaGemRepository.findGems(PageRequest.of(0, 5));

//        assertFalse(allGems.isEmpty());
    }

    @Test
    void save() throws JsonProcessingException {

        GemEntity gemEntity = buildGemEntity();
        Set<Amount> prices = Set.of(Amount.ofPhp(54.2), Amount.ofPhp(54.5), Amount.ofPhp(54.1), Amount.ofPhp(52.2));
        gemEntity = gemEntity.toBuilder()
                .prices(prices)
                .build();
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setOwner("Sandro");
        historyEntity.setTransferredDate(Instant.now());
        gemEntity.addHistory(historyEntity);
        GemEntity savedGemEntity = jpaGemRepository.save(gemEntity);

        assertNotNull(savedGemEntity.getId());
        Optional<GemEntity> optionalGemEntity = jpaGemRepository.findById(savedGemEntity.getId());
        assertTrue(optionalGemEntity.isPresent());
        assertEquals(gemEntity, optionalGemEntity.get());
        assertEquals(prices, savedGemEntity.getPrices(), "prices");
        log.info("Gem={}", savedGemEntity);


    }

    private static GemEntity buildGemEntity() {
        return GemEntity.builder()
                .name("Amytyst")
                .rrn(UUID.randomUUID())
                .cut("round")
                .color("RED")
                .clarity("MEDIUM")
                .status(GemStatus.INACTIVE)
                .histories(new HashSet<>())
                .build();
    }

    private void loadGems() {
        GemEntity gemEntity = buildGemEntity();
        Set<Amount> prices = Set.of(Amount.ofPhp(54.2), Amount.ofPhp(54.5), Amount.ofPhp(54.1), Amount.ofPhp(52.2));
        gemEntity = gemEntity.toBuilder()
                .prices(prices)
                .build();
//        HistoryEntity historyEntity = new HistoryEntity();
//        historyEntity.setOwner("Sandro");
//        historyEntity.setTransferredDate(Instant.now());
//        gemEntity.addHistory(historyEntity);
//
//        HistoryEntity sandra = new HistoryEntity();
//        sandra.setOwner("Sandra");
//        sandra.setTransferredDate(Instant.now());
//
//        gemEntity.addHistory(sandra);
        GemEntity savedGemEntity = jpaGemRepository.saveAndFlush(gemEntity);
    }
}
