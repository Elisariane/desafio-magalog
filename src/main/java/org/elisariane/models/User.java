package org.elisariane.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class User {
    @JsonProperty("user_id")
    private final int userId;

    private final String name;

    @JsonProperty("orders")
    private final Map<Integer, Order> ordersMap;

    public User(int userId, String name, Map<Integer, Order> orders) {
        this.userId = userId;
        this.name = name;
        this.ordersMap = orders;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("orders")
    public Map<Integer, Order> getOrdersMap() {
        return ordersMap;
    }

    public void addOrder(Order order) {
        ordersMap.put(order.getOrderId(), order);
    }

    public Order getOrder(int orderId) {
        return ordersMap.get(orderId);
    }
}