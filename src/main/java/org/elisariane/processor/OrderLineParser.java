package org.elisariane.processor;

import org.elisariane.dtos.OrderLine;
import org.elisariane.exceptions.UnparseableLineException;
import org.elisariane.exceptions.WrongLineSizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderLineParser {

    private final Logger logger = LoggerFactory.getLogger(OrderLineParser.class);
    private static final int EXPECTED_LINE_LENGTH = 95;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public OrderLine parse(String line) {
        validateLineSize(line);

        try {
            return extractOrderLine(line);
        } catch (UnparseableLineException exception) {
            throw new UnparseableLineException("Erro ao converter a linha: " + line, exception);
        }
    }

    private void validateLineSize(String line) {
        if (line.length() != EXPECTED_LINE_LENGTH) {
            logger.error("Linha com tamanho inválido ({} caracteres). Esperado: {}. Pulando linha...", line.length(), EXPECTED_LINE_LENGTH);
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
