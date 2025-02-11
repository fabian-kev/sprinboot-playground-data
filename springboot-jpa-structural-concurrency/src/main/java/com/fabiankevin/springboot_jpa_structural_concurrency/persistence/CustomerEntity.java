package com.fabiankevin.springboot_jpa_structural_concurrency.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Entity(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<OrderEntity> orders;

    public void addOrder(OrderEntity order){
        this.orders.add(order);
        order.setCustomer(this);
    }

}
