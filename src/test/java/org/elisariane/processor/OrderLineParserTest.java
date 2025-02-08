package org.elisariane.processor;

import org.elisariane.dtos.OrderLine;
import org.elisariane.exceptions.WrongLineSizeException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineParserTest {

    private final OrderLineParser orderLineParser = new OrderLineParser();

    @Test
    void whenTryToProcessValidLinePasing_thenReturnSuccess() {
        String line = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";

        OrderLine orderLine = orderLineParser.parse(line);

        assertNotNull(orderLine);
        assertEquals(70, orderLine.userId());
        assertEquals("Palmer Prosacco", orderLine.name());
        assertEquals(753, orderLine.orderId());
        assertEquals(3, orderLine.productId());
        assertEquals(new BigDecimal("1836.74"), orderLine.productValue());
        assertEquals(LocalDate.of(2021, 3, 8), orderLine.date());
    }

    @Test
    void whenTryToProcessInvalidLineParsingWithGreaterNumbersOfLines_thenReturnWrongLineSizeException() {
        String line = "0000000070                                    Palmer Prosacco00000007530000000003           1836.7420210308";

        assertThrows(WrongLineSizeException.class, () -> orderLineParser.parse(line));
    }

}