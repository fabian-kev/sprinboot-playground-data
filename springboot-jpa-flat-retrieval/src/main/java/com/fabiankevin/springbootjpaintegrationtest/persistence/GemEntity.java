package com.fabiankevin.springbootjpaintegrationtest.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "gems")
@Table(name = "gems")
@Builder(toBuilder = true)
@ToString(exclude = "histories")
public class GemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID rrn;
    private String name;
    private String color;
    //    high, low, medium
    private String clarity;
    //    round, oval, emerald, or princess cut
    private String cut;
    private Instant createdDate;
    @OneToMany(mappedBy = "gem", cascade = CascadeType.ALL)
    private Set<HistoryEntity> histories = new HashSet<>();

    public void addHistory(HistoryEntity history) {
        histories.add(history);
        history.setGem(this);
    }
}
