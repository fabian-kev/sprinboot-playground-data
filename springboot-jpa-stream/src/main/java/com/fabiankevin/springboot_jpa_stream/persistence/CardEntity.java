package com.fabiankevin.springboot_jpa_stream.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@ToString(exclude = "customer")
@EqualsAndHashCode(exclude = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
@Entity(name = "cards")
@Builder
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;
    private String name;
    private String cardNo;
    private Instant createdAt;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
