package org.elisariane.utils.converter;

import org.elisariane.models.Order;
import org.elisariane.models.Product;
import org.elisariane.models.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterTest {

    @Test
    void whenTryToConverterToJSON_thenReturnSuccess() throws Exception {
        Product product = new Product(12, new BigDecimal("123456789.01"));
        Order order = new Order(1, LocalDate.of(2023, 2, 1));
        order.addProduct(product);

        Map<Integer, Order> orders = new HashMap<>();
        orders.put(1, order);

        User user = new User(1, "John Doe", orders);

        String json = JsonConverter.toJson(List.of(user));

        assertNotNull(json);
        assertTrue(json.contains("John Doe"));
        assertTrue(json.contains("123456789.01"));
    }

}