package com.thelastcodebenders.social_commerce_be.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thelastcodebenders.social_commerce_be.models.entities.Product;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class ProductRequest {
    private String name;
    private double amount;
    private MultipartFile image;
    @JsonIgnore
    private UUID userId;

    public Product toDb(UUID userId, String imageUrl) {
        return Product.builder()
                .name(name)
                .amount(amount)
                .userId(userId)
                .imageUrl(imageUrl)
                .build();
    }
}
