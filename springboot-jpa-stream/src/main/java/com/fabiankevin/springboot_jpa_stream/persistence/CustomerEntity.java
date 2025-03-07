package com.fabiankevin.springboot_jpa_stream.persistence;

import com.fabiankevin.springboot_jpa_stream.models.CustomerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Entity(name = "customers")
@Builder
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
    private Instant createdAt;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<CardEntity> cards = new HashSet<>();

    public void addCard(CardEntity cardEntity) {
        cardEntity.setCustomer(this);
        cards.add(cardEntity);
    }
}
