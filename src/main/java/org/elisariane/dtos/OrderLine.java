package org.elisariane.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderLine(
        int userId,
        String name,
        int orderId,
        int productId,
        BigDecimal productValue,
        LocalDate date
) {

}
