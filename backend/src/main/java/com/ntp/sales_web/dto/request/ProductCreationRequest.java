package com.ntp.sales_web.dto.request;

import java.math.BigDecimal;

public class ProductCreationRequest {
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }
}
