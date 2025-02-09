package com.fabiankevin.spring_caching_redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    private UUID id;
    private String name;
}
