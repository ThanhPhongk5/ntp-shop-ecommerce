package com.ntp.sales_web.service;

import com.ntp.sales_web.dto.request.AddToCartRequest;
import com.ntp.sales_web.dto.response.CartResponse;
import com.ntp.sales_web.entity.Cart;
import com.ntp.sales_web.entity.CartItem;
import com.ntp.sales_web.entity.Product;
import com.ntp.sales_web.entity.User;
import com.ntp.sales_web.exception.NotFoundException;
import com.ntp.sales_web.mapper.CartMapper;
import com.ntp.sales_web.repository.CartItemRepository;
import com.ntp.sales_web.repository.CartRepository;
import com.ntp.sales_web.repository.ProductRepository;
import com.ntp.sales_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    public CartResponse addToCart(Long userId, Long productId, Long quantity) {
        // tìm user dựa trên userId
        User user=userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        // tìm hoặc tạo cart cho user
        Cart cart=cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart=new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        //tìm product
        Product product=productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        // thêm cartItem
        // Kiểm tra xem trong cart đã có product đó chưa
        for(CartItem item: cart.getItems()){
            if(item.getProduct().getId().equals(product.getId())){
                item.AddToCartItem(quantity);
                return CartMapper.toCartResponse(cartRepository.save(cart));
            }
        }
        // tạo mới nếu chưa có
        CartItem cartItem=new CartItem(product,product.getPrice(),quantity,cart);
        cart.additem(cartItem);
        return CartMapper.toCartResponse(cartRepository.save(cart));
    }
    public CartResponse getCartByUser(Long userid){
        return CartMapper.toCartResponse(cartRepository.findByUserId(userid).orElseThrow(() -> new RuntimeException(("User not found"))));
    }
    public void deleteItemfromCart(Long userid,Long productid){
        Cart cart=cartRepository.findByUserId(userid).orElseThrow(() -> new NotFoundException(("Cart not found")));
        Iterator<CartItem> iterator=cart.getItems().iterator();
        while(iterator.hasNext()){
            CartItem item=iterator.next();
            if(item.getProduct().getId().equals(productid)) {
                iterator.remove();
            }
        }
        cartRepository.save(cart);
    }
}
