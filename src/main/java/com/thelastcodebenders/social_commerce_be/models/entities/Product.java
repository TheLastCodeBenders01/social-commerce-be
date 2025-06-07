package com.thelastcodebenders.social_commerce_be.models.entities;

import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    private String name;
    private double amount;
    private String imageUrl;

    private UUID userId;

    public ProductResponse toDto() {
        return ProductResponse.builder()
                .amount(amount)
                .name(name)
                .userId(userId)
                .productId(productId)
                .imageUrl(imageUrl)
                .build();
    }
}
