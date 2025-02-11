package com.fabiankevin.springboot_jpa_structural_concurrency.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(exclude = "customer")
@ToString(exclude = "customer")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String itemName;
    private Double amount;
    private Instant orderDate;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerEntity customer;
}