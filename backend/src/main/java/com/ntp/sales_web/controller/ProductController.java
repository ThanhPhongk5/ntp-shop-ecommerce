package com.ntp.sales_web.controller;

import com.ntp.sales_web.dto.request.ProductCreationRequest;
import com.ntp.sales_web.dto.request.ProductUpdateRequest;
import com.ntp.sales_web.entity.Product;
import com.ntp.sales_web.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")

public class ProductController {
    private final ProductService productService;
    @GetMapping
    public List<Product> getAll(){
        return productService.getAll();
    }
    @PostMapping
    public Product create(@RequestBody ProductCreationRequest request){
        return productService.create(request);
    }
    @GetMapping("/{id}")
    public Optional<Product> getOne(@PathVariable("id") Long id){
        return productService.getproduct(id);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        productService.deleteproduct(id);
        return "Đã xóa sản phẩm";
    }
    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Long id,@RequestBody ProductUpdateRequest request){
        return productService.update(id,request);
    }
    @Value("${file.upload-dir}")
    private String uploadDir;
    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            productService.updateProductImage(id, fileName);

            return ResponseEntity.ok("Thành công: " + fileName);

        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi upload: " + e.getMessage());
        }
    }
}
