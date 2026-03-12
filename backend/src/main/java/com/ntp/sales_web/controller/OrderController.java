package com.ntp.sales_web.controller;

import com.ntp.sales_web.entity.Order;
import com.ntp.sales_web.repository.OrderRepository;
import com.ntp.sales_web.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor

public class OrderController {
    private final OrderService orderService;
    @PostMapping("/create/{userid}")
    public Order createOrder(@PathVariable Long userid){
        return orderService.createOrder(userid);

    }
    @GetMapping("/user/{userid}")
    public List<Order> getOrders(@PathVariable Long userid){
        return orderService.getOrderByUser(userid);
    }
}
