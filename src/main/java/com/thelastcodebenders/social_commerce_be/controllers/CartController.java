package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.CartResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.PaymentResponse;
import com.thelastcodebenders.social_commerce_be.services.CartService;
import com.thelastcodebenders.social_commerce_be.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final OrderService orderService;

    @Operation(summary = "add procuct to cart")
    @PostMapping("{productId}")
    public CartResponse addItemToCart(@PathVariable Long productId) {
        return cartService.addItemToCart(productId);
    }

    @Operation(summary = "get cart by user")
    @GetMapping
    public CartResponse getCartByUser() {
        return cartService.getCartByUser();
    }

    @GetMapping("initiate-checkout")
    public PaymentResponse initiateCheckout() {
        return orderService.initiateCheckout();
    }
}
