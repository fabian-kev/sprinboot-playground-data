package com.fabiankevin.springboot_jpa_structural_concurrency.services;

import com.fabiankevin.springboot_jpa_structural_concurrency.persistence.CustomerEntity;
import com.fabiankevin.springboot_jpa_structural_concurrency.persistence.CustomerRepository;
import com.fabiankevin.springboot_jpa_structural_concurrency.persistence.OrderEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Component
@Transactional(REQUIRES_NEW)
@RequiredArgsConstructor
@Slf4j
public class ChunkExecutor {
    private final CustomerRepository customerRepository;

    public synchronized void process(String taskName, List<CustomerEntity> customerEntityList) {
        List<CustomerEntity> list = customerEntityList.stream()
                .peek(customerEntity -> customerEntity.addOrder(OrderEntity.builder()
                        .orderDate(Instant.now())
                        .amount(70.0)
                        .itemName(UUID.randomUUID().toString())
                        .build()))
                .map(customerEntity -> {
                    customerEntity.setStatus("ACTIVE");
                    return customerEntity;
                })
                .toList();
        customerRepository.saveAll(list);
        log.info("{} has been completed.", customerEntityList.size());
    }
}
