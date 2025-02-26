package com.fabiankevin.springboot_jpa_stream.services;

import com.fabiankevin.springboot_jpa_stream.models.CustomerStatus;
import com.fabiankevin.springboot_jpa_stream.persistence.CustomerEntity;
import com.fabiankevin.springboot_jpa_stream.persistence.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CustomerServiceUsingStream {
    private final CustomerRepository customerRepository;
    private static final int DEFAULT_CHUNK_SIZE = 10_000;

    public void process() {
        List<CustomerEntity> customerEntityList = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        Instant now = Instant.now();
        try (Stream<CustomerEntity> allInactiveInStream = customerRepository.findAllInactiveInStream()) {
            allInactiveInStream
                    .forEach(customerEntity -> {
                        customerEntityList.add(customerEntity);
                        if (customerEntityList.size() >= DEFAULT_CHUNK_SIZE) {
                            log.info("processing: {}", customerEntityList.size());
                            Set<UUID> idsToUpdate = customerEntityList.stream()
                                    .map(CustomerEntity::getId)
                                    .collect(Collectors.toSet());
                            customerRepository.updateStatus(CustomerStatus.ACTIVE, idsToUpdate);
                            customerEntityList.clear();
                        }
                        atomicInteger.incrementAndGet();
                    });
        }// stream will be closed here automatically
        Set<UUID> idsToUpdate = customerEntityList.stream()
                .map(CustomerEntity::getId)
                .collect(Collectors.toSet());
        customerRepository.updateStatus(CustomerStatus.ACTIVE, idsToUpdate);
        log.info("Duration: {}", Duration.between(Instant.now(), now));
        log.info("Total processed count: {}", atomicInteger.get());
    }
}
