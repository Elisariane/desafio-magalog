package org.elisariane.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.elisariane.models.User;

import java.io.IOException;
import java.util.List;

public class JsonConverter {
    public static String toJson(List<User> users) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
    }
}
