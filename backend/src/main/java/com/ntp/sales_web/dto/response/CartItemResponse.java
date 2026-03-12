package com.ntp.sales_web.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CartItemResponse {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Long quantity;

    public CartItemResponse(Long productId, String productName, BigDecimal price, Long quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

}
