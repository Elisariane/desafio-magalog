package org.elisariane.processor;

import org.elisariane.dtos.OrderLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class LineParser {

    private final Logger logger = LoggerFactory.getLogger(LineParser.class);
    private static final int EXPECTED_LINE_LENGTH = 95;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public Optional<OrderLine> parseLine(String line) {
        if (line.length() != EXPECTED_LINE_LENGTH) {
            logger.error("Linha com tamanho inválido: {}. Esperando " +
                    "{} linhas. Por tanto pulando essa linha...", line.length(), EXPECTED_LINE_LENGTH);
            return Optional.empty();
        }

        try {
            int userId = Integer.parseInt(line.substring(0, 10).trim());
            String name = line.substring(10, 55).trim();
            int orderId = Integer.parseInt(line.substring(55, 65).trim());
            int productId = Integer.parseInt(line.substring(65, 75).trim());
            BigDecimal productValue = new BigDecimal(line.substring(75, 87).trim());
            LocalDate date = LocalDate.parse(line.substring(87, 95).trim(), DATE_FORMATTER);

            return Optional.of(new OrderLine(userId, name, orderId, productId, productValue, date));
        } catch (NumberFormatException | DateTimeParseException exception) {
            logger.error("Erro ao analisar a linha: {}", exception.getMessage());
        }

        return Optional.empty();
    }

}
