package org.elisariane.processor;

import org.elisariane.dtos.OrderLine;
import org.elisariane.exceptions.WrongLineSizeException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderLineParser {

    private static final int EXPECTED_LINE_LENGTH = 95;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public OrderLine parse(String line) {
        validateLineSize(line);

        return extractOrderLine(line);
    }

    private void validateLineSize(String line) {
        if (line.length() != EXPECTED_LINE_LENGTH) {
            throw new WrongLineSizeException("Tamanho de linha inválido.", EXPECTED_LINE_LENGTH, line.length());
        }
    }

    private OrderLine extractOrderLine(String line) {
        int userId = Integer.parseInt(line.substring(0, 10).trim());
        String name = line.substring(10, 55).trim();
        int orderId = Integer.parseInt(line.substring(55, 65).trim());
        int productId = Integer.parseInt(line.substring(65, 75).trim());
        BigDecimal productValue = new BigDecimal(line.substring(75, 87).trim());
        LocalDate date = LocalDate.parse(line.substring(87, 95).trim(), DATE_FORMATTER);

        return new OrderLine(userId, name, orderId, productId, productValue, date);
    }

}
