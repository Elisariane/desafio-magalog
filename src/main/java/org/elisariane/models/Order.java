package org.elisariane.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    @JsonProperty("order_id")
    private final int orderId;
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private BigDecimal total = BigDecimal.ZERO;
    private final List<Product> products;

    public Order(int orderId, LocalDate date) {
        this.orderId = orderId;
        this.date = date;
        this.products = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTotal() {
        return total.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        total = total.add(product.getValue());
    }
}
