package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.CartResponse;
import com.thelastcodebenders.social_commerce_be.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("carts")
@RestController
public class CartController {
    private final CartService cartService;

    @PostMapping("{productId}")
    public CartResponse addItemToCart(@PathVariable Long productId) {
        return cartService.addItemToCart(productId);
    }

    @GetMapping
    public CartResponse getCartById() {
        return cartService.getCartById();
    }
}
