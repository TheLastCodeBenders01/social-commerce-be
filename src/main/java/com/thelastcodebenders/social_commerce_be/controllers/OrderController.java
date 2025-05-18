package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.exceptions.OrderNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.KorapayWebhookRequest;
import com.thelastcodebenders.social_commerce_be.services.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Hidden
    @PostMapping("payments/confirm")
    public AppResponse successfulPayment(@RequestBody Map<String, Object> request) throws OrderNotFoundException {

        return orderService.successfulPayment(request);
    }
}
