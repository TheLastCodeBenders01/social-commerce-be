package com.thelastcodebenders.social_commerce_be.models.dto;

import com.thelastcodebenders.social_commerce_be.models.entities.Product;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductRequest {
    private String name;
    private double amount;
    private UUID userId;

    public Product toDb() {
        return Product.builder()
                .name(name)
                .amount(amount)
                .userId(UserUtil.getLoggedInUser().getUserId())
                .build();
    }
}
