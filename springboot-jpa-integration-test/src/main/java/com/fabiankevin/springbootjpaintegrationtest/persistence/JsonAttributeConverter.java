package com.fabiankevin.springbootjpaintegrationtest.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class JsonAttributeConverter<T> implements AttributeConverter<T, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(T doubleList) {
        try {
            return objectMapper.writeValueAsString(doubleList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T convertToEntityAttribute(String s) {
        try {
            if (s == null || s.isEmpty()) {
                return null;
            }
            return objectMapper.readValue(s, new TypeReference<T>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
