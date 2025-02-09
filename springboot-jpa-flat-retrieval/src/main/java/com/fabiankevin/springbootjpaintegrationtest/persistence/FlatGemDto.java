package com.fabiankevin.springbootjpaintegrationtest.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlatGemDto {
    private Long id;
    private UUID rrn;
    private String name;
    private String owner;
}
