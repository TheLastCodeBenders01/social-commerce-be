package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponse {
    private UUID orderId;
    private UUID userId;
    private double totalAmount;
    private boolean paid;
    private List<ProductResponse> products;
}
