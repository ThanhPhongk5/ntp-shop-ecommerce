package com.ntp.sales_web.service;

import com.ntp.sales_web.entity.Cart;
import com.ntp.sales_web.entity.CartItem;
import com.ntp.sales_web.entity.Order;
import com.ntp.sales_web.entity.OrderItem;
import com.ntp.sales_web.exception.NotFoundException;
import com.ntp.sales_web.repository.CartRepository;
import com.ntp.sales_web.repository.OrderRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    public Order createOrder(Long userid){
        Cart cart =cartRepository.findByUserId(userid).orElseThrow(() -> new NotFoundException("CART NOT FOUND"));
        if(cart.getItems().isEmpty()){
            throw new RuntimeException(("CART IS EMPTY"));
        }
        Order order=new Order();
        order.setUser(cart.getUser());
        BigDecimal total=BigDecimal.ZERO;
        for(CartItem item: cart.getItems()){
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(item.getProduct().getId());
            orderItem.setProductName(item.getProduct().getName());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            total=total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

        }
        order.setTotalPrice(total);
        cart.getItems().clear();
        return orderRepository.save(order);
    }
    public List<Order> getOrderByUser(Long userid){
        return orderRepository.findByUserId(userid);
    }
}
