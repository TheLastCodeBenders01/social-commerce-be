package com.thelastcodebenders.social_commerce_be.models.dto;

import com.thelastcodebenders.social_commerce_be.models.entities.Product;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private double amount;

    public Product toDb() {
        return Product.builder()
                .name(name)
                .amount(amount)
                .build();
    }
}
