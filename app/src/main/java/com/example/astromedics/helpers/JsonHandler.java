package com.example.astromedics.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHandler {
    public static String toJson(Object object){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }
}
