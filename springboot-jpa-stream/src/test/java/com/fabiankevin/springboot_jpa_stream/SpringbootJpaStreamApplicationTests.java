package com.fabiankevin.springboot_jpa_stream;

import com.fabiankevin.springboot_jpa_stream.models.CustomerStatus;
import com.fabiankevin.springboot_jpa_stream.persistence.CustomerEntity;
import com.fabiankevin.springboot_jpa_stream.persistence.CustomerRepository;
import com.fabiankevin.springboot_jpa_stream.services.CustomerServiceUsingStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringbootJpaStreamApplicationTests {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerServiceUsingStream customerService;

    @Test
    void process_givenInactiveCustomers_thenShouldBeUpdatedToActive() {
        List<CustomerEntity> entities = new ArrayList<>();
        IntStream.range(0, 1_000_000)
                .boxed()
                .map(i -> {
                    CustomerEntity customerEntity = new CustomerEntity();
                    customerEntity.setName(UUID.randomUUID().toString());
                    customerEntity.setStatus(CustomerStatus.INACTIVE);
                    customerEntity.setCreatedAt(Instant.now());
                    return customerEntity;
                }).forEach(entity -> {
                    entities.add(entity);
                    if (entities.size() % 1000 == 0) {
                        customerRepository.saveAllAndFlush(entities);
                        entities.clear();
                    }
                });

        customerService.process();

        long count = customerRepository.count(Example.of(CustomerEntity.builder()
                .status(CustomerStatus.ACTIVE)
                .build()));
        assertEquals(1_000_000, count, "should be equal");
    }

}
