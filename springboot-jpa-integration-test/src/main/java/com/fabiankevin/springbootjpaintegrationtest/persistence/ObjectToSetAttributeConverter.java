package com.fabiankevin.springbootjpaintegrationtest.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.util.Set;

public class ObjectToSetAttributeConverter implements AttributeConverter<Set<Amount>, Object> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<Amount> amounts) {
        try {
            return objectMapper.writeValueAsString(amounts);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Amount> convertToEntityAttribute(Object s) {
        try {
            return objectMapper.readValue(String.valueOf(s), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
