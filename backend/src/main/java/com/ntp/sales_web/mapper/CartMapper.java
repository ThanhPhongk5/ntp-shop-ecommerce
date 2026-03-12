package com.ntp.sales_web.mapper;

import com.ntp.sales_web.dto.response.CartItemResponse;
import com.ntp.sales_web.dto.response.CartResponse;
import com.ntp.sales_web.entity.Cart;
import com.ntp.sales_web.entity.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static CartItemResponse toCartItemResponse(CartItem cartItem) {
        return new CartItemResponse(cartItem.getProduct().getId(),cartItem.getProduct().getName(),cartItem.getPrice()
        ,cartItem.getQuantity());
    }
    public static CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> listItem=cart.getItems().stream().map(CartMapper::toCartItemResponse).collect(Collectors.toList());
        return new CartResponse(cart.getId(),cart.getUser().getId(),cart.getUser().getUsername(),listItem);
    }
}
