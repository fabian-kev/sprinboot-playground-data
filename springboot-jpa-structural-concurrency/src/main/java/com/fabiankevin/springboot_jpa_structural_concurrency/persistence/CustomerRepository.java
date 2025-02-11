package com.fabiankevin.springboot_jpa_structural_concurrency.persistence;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    List<CustomerEntity> findAllByStatus(String status, PageRequest pageRequest);
}
