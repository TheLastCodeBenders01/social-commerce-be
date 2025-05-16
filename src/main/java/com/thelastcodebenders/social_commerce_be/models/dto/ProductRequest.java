package com.thelastcodebenders.social_commerce_be.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thelastcodebenders.social_commerce_be.models.entities.Product;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductRequest {
    private String name;
    private double amount;
    @JsonIgnore
    private UUID userId;

    public Product toDb(UUID userId) {
        return Product.builder()
                .name(name)
                .amount(amount)
                .userId(userId)
                .build();
    }
}
