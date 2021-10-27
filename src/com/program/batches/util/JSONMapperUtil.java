package com.program.batches.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONMapperUtil {

    public static <T> String toString( T object ) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject( final String jsonString, Class<T> clazz ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
