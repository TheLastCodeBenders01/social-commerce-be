package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
@Data
@Builder
public class ProductResponse {
    private String name;
    private double amount;
    private UUID userId;
    private long productId;
    private String imageUrl;
}
