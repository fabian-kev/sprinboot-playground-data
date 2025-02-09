package com.fabiankevin.springbootjpaintegrationtest.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlatGemDto {
    private Long id;
    private UUID rrn;
    private String name;
    private String color;
    private String clarity;
    private String cut;
    private Instant transferredDate;
    //    @Convert(converter = JsonAttributeConverter.class)
//    private Set<Amount> prices;
    private Object prices;
    private String owner;

}
