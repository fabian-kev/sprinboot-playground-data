package com.fabiankevin.springbootjpaintegrationtest.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GemFlatDto {
    private Long id;
    private UUID rrn;
    private String name;
    private String owner;
//    private String color;
//    //    high, low, medium
//    private String clarity;
//    //    round, oval, emerald, or princess cut
//    private String cut;
//    private String jsonPrices;
//    private Instant createdDate;
//    private GemStatus status;
}
