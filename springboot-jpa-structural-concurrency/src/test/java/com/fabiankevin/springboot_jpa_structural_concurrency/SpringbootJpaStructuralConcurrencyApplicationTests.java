package com.fabiankevin.springboot_jpa_structural_concurrency;

import com.fabiankevin.springboot_jpa_structural_concurrency.persistence.CustomerEntity;
import com.fabiankevin.springboot_jpa_structural_concurrency.persistence.CustomerRepository;
import com.fabiankevin.springboot_jpa_structural_concurrency.services.CustomerBatchUsingClassicStructuralConcurrencyPartitioner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringbootJpaStructuralConcurrencyApplicationTests {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerBatchUsingClassicStructuralConcurrencyPartitioner service;

    @BeforeEach
    public void beforeEach() {
        Set<CustomerEntity> pending = IntStream.range(0, 1_000_000).boxed()
                .map(i -> {
                    CustomerEntity customerEntity = new CustomerEntity();
                    customerEntity.setName(UUID.randomUUID().toString());
                    customerEntity.setStatus("PENDING");
                    return customerEntity;
                }).collect(Collectors.toSet());
        customerRepository.saveAll(pending);
    }

    @Test
    void contextLoads() {
        service.run();

        long count = customerRepository.count(Example.of(
                CustomerEntity.builder()
                        .status("ACTIVE")
                        .build()
        ));

        assertEquals(1_000_000, count);
    }

}
