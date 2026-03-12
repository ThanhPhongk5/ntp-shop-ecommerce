package com.ntp.sales_web.dto.request;

public class AddToCartRequest {
    private Long productId;
    private Long quantity;

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
