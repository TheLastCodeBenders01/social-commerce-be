package com.thelastcodebenders.social_commerce_be.models.entities;

import com.thelastcodebenders.social_commerce_be.models.dto.OrderResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.UserResponse;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Builder.Default private UUID orderId = UUID.randomUUID();

    private UUID userId;

    @Transient
    private double totalAmount;

    @Builder.Default private boolean paid = false;

    @Builder.Default private Instant createdAt = Instant.now();
    @Builder.Default private Instant updatedAt = Instant.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    @Builder.Default private List<Long> productIds = new ArrayList<>();

    public OrderResponse toDto(List<ProductResponse> products, User user) {
        double totalAmount = products.parallelStream().mapToDouble(ProductResponse::getAmount).sum();
        return OrderResponse.builder()
                .address(
                        UserResponse.Address.builder()
                                .streetAddress(user.getStreetAddress())
                                .state(user.getState())
                                .country(user.getCountry())
                        .build()
                )
                .orderId(orderId)
                .userId(userId)
                .paid(paid)
                .totalAmount(totalAmount)
                .products(products)
                .createdAt(createdAt)
                .build();
    }
}
