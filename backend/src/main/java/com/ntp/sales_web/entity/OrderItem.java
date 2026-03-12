package com.ntp.sales_web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="oder_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal price; // giá ở thời điểm mua
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
