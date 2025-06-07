package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.ProductRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import com.thelastcodebenders.social_commerce_be.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("products")
@RestController
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "get product by id")
    @GetMapping("{productId}")
    public ProductResponse getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId).toDto();
    }

    @Operation(summary = "get user products")
    @GetMapping
    public List<ProductResponse> getUserProducts() {
        return productService.getUserProducts();
    }

    @Operation(summary = "create products")
    @PostMapping
    public ProductResponse addPostProducts(@ModelAttribute ProductRequest request) {
        return productService.saveProduct(request);
    }
}
