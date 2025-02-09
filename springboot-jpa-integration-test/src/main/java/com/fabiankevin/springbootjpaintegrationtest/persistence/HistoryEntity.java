package com.fabiankevin.springbootjpaintegrationtest.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(exclude = "gem")
@ToString(exclude = "gem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "histories")
@Table(name = "histories")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String owner;
    private Instant transferredDate;
    @ManyToOne
    @JoinColumn(name = "gem_id")
    private GemEntity gem;
}
