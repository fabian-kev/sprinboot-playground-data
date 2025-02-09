package com.fabiankevin.springboot_multiple_datasources.models;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@With
public class Weather {
    UUID id;
    String name;
    Instant createdDate;
}
