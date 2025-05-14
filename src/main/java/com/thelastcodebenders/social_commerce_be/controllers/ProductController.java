package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import com.thelastcodebenders.social_commerce_be.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("products")
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping("{productId}")
    public ProductResponse getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId).toDto();
    }

    @GetMapping
    public List<ProductResponse> getUserProducts() {
        return productService.getUserProducts();
    }
}
