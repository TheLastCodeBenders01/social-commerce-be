package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.exceptions.OrderNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.KorapayWebhookRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.OrderResponse;
import com.thelastcodebenders.social_commerce_be.services.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Hidden
    @PostMapping("payments/confirm")
    public AppResponse successfulPayment(@RequestBody KorapayWebhookRequest request) throws OrderNotFoundException {
        return orderService.successfulPayment(request);
    }

    @Operation(summary = "get order by id")
    @GetMapping("{orderId}")
    public OrderResponse getOrderById(@PathVariable UUID orderId) {
        return orderService.findOrderById(orderId);
    }

    @Operation(summary = "get user orders")
    @GetMapping
    public List<OrderResponse> getUserOrders() {
        return orderService.getUserOrders();
    }

    @Operation(summary = "get purchased orders for vendor")
    @GetMapping("vendors/me")
    public List<OrderResponse> getPurchasedOrdersForVendor() {
        return orderService.getPurchasedOrdersForVendor();
    }
}
