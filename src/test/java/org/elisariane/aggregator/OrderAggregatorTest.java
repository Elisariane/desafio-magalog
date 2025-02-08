package org.elisariane.aggregator;

import org.elisariane.dtos.OrderLine;
import org.elisariane.models.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderAggregatorTest {

    private final OrderAggregator orderAggregator = new OrderAggregator();

    @Test
    void whenTryAggregateSingleOrderLine_thenReturnSuccess() {
        OrderLine orderLine = new OrderLine(1, "John Doe", 1, 12, new BigDecimal("1000.50"), LocalDate.of(2023, 2, 1));

        User user = orderAggregator.aggregate(orderLine);

        assertNotNull(user);
        assertEquals(1, user.getUserId());
        assertEquals("John Doe", user.getName());
        assertTrue(user.getOrdersMap().containsKey(1));
        assertEquals(1, user.getOrdersMap().get(1).getOrderId());
        assertEquals("1000.50", user.getOrdersMap().get(1).getTotal());
    }

    @Test
    void whenTryAggregateMultipleOrderLines_thenReturnSuccess() {
        OrderLine orderLine1 = new OrderLine(1, "John Doe", 1, 12, new BigDecimal("123456789.01"), LocalDate.of(2023, 2, 1));
        OrderLine orderLine2 = new OrderLine(1, "John Doe", 2, 13, new BigDecimal("987654321.01"), LocalDate.of(2023, 2, 1));

        orderAggregator.aggregate(orderLine1);
        orderAggregator.aggregate(orderLine2);

        User user = orderAggregator.getAggregatedUsers().getFirst();

        assertNotNull(user);
        assertEquals(1, user.getUserId());
        assertEquals("John Doe", user.getName());
        assertEquals("123456789.01", user.getOrdersMap().get(1).getTotal());
        assertEquals(2, user.getOrdersMap().size());
    }

}