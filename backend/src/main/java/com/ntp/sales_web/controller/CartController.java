package com.ntp.sales_web.controller;

import com.ntp.sales_web.dto.request.AddToCartRequest;
import com.ntp.sales_web.dto.response.CartResponse;
import com.ntp.sales_web.entity.Cart;
import com.ntp.sales_web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")

public class CartController {
    private final CartService cartService;
    @PostMapping("/add/{userid}")
    public CartResponse add (@PathVariable("userid") Long userid, @RequestBody
    AddToCartRequest request){
        return cartService.addToCart(userid,request.getProductId(),request.getQuantity());
    }
    @GetMapping("/{userid}")
    public CartResponse get(@PathVariable("userid") Long userid){
        return cartService.getCartByUser((userid));
    }
    @DeleteMapping("/{userId}/item/{productId}")
    public String delete(@PathVariable("userId") Long userId,@PathVariable("productId") Long productId){
        cartService.deleteItemfromCart(userId,productId);
        return "Đã xóa sản phẩm khỏi giỏ hàng";
    }

}
