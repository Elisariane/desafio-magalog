package org.elisariane.aggregator;

import org.elisariane.dtos.OrderLine;
import org.elisariane.models.Order;
import org.elisariane.models.Product;
import org.elisariane.models.User;

import java.util.*;

public class OrderAggregator {
    private final Map<Integer, User> userMap = new LinkedHashMap<>();

    public User aggregate(OrderLine orderLine) {
        User user = userMap.computeIfAbsent(orderLine.userId(), id ->
                new User(orderLine.userId(), orderLine.name(), new HashMap<>()));
        Order order = user.getOrdersMap().computeIfAbsent(orderLine.orderId(), id ->
                new Order(orderLine.orderId(), orderLine.date())
        );
        order.addProduct(new Product(orderLine.productId(), orderLine.productValue()));
        return user;
    }

    public List<User> getAggregatedUsers() {
        return new ArrayList<>(userMap.values());
    }

}
