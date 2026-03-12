package com.ntp.sales_web.service;

import com.ntp.sales_web.dto.request.ProductCreationRequest;
import com.ntp.sales_web.dto.request.ProductUpdateRequest;
import com.ntp.sales_web.entity.Product;
import com.ntp.sales_web.exception.NotFoundException;
import com.ntp.sales_web.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAll (){
        return productRepository.findAll();
    }
    public Product create(ProductCreationRequest request){
        Product p=new Product(request.getName(),request.getPrice(), request.getStock(), request.getDescription());
        return productRepository.save(p);
    }
    public Optional<Product> getproduct(Long id){
        return productRepository.findById(id);
    }
    public void deleteproduct(Long id){
        productRepository.deleteById(id);
    }
    public Product update(Long id, ProductUpdateRequest request){
        Product p= productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        p.setName(request.getName());
        p.setPrice(request.getPrice());
        p.setStock(request.getStock());
        p.setDescription(request.getDescription());
        return productRepository.save(p);
    }
    public void updateProductImage(Long id, String fileName) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));

        product.setImage(fileName);
        productRepository.save(product);
    }
}
