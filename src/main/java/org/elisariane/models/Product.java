package org.elisariane.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Product {

    @JsonProperty("product_id")
    private final int productId;
    private final BigDecimal value;

    public Product(int productId, BigDecimal value) {
        this.productId = productId;
        this.value = value;
    }

    public int getProductId() {
        return productId;
    }

    public BigDecimal getValue() {
        return value;
    }

    @JsonProperty("value")
    public String getValueFormatted() {
        return value.setScale(2, RoundingMode.HALF_UP).toString();
    }
}
