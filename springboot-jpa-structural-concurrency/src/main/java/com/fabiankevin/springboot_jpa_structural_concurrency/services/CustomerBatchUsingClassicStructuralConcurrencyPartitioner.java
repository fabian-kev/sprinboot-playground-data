package com.fabiankevin.springboot_jpa_structural_concurrency.services;

import com.fabiankevin.springboot_jpa_structural_concurrency.persistence.CustomerEntity;
import com.fabiankevin.springboot_jpa_structural_concurrency.persistence.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerBatchUsingClassicStructuralConcurrencyPartitioner {
    private final CustomerRepository customerRepository;
    private final ChunkExecutor chunkExecutor;

    public void run() {
        Instant now = Instant.now();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        try {
            int gridSize = 200;
            List<CustomerEntity> customerEntities;
            while (!(customerEntities = customerRepository.findAllByStatus("PENDING", PageRequest.ofSize(100_000))).isEmpty()) {
                List<List<CustomerEntity>> partitionedCustomers = new ArrayList<>();
                int partitionSize = 500;
                for (int i = 0; i < gridSize; i++) {
                    partitionedCustomers.add(customerEntities.subList(partitionSize * i, partitionSize * (i+1)));
                }

                List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
                for (List<CustomerEntity> partitionedCustomer : partitionedCustomers) {
                    CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> chunkExecutor.process("test", partitionedCustomer), executor);
                    completableFutures.add(voidCompletableFuture);
                }

                // Combine the results of all tasks
                CompletableFuture<Void> allTasks = CompletableFuture.allOf(completableFutures.stream()
                        .toArray(CompletableFuture[]::new));

                // Wait for all tasks to complete
                allTasks.join();
            }
            log.info("Run Duration: {}", Duration.between(Instant.now(), now));
        } finally {
            executor.shutdown();
        }
    }
}
