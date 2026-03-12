package com.ntp.sales_web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="cart_item")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private BigDecimal price;
    private Long quantity;
    @ManyToOne
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;

    public CartItem(Product product,BigDecimal price, Long quantity, Cart cart) {
        this.product = product;
        this.quantity = quantity;
        this.cart = cart;
        this.price=price;
    }
    public void AddToCartItem(Long quantity){
        this.quantity=this.quantity+quantity;
    }
}
