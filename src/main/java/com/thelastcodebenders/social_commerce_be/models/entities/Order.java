package com.thelastcodebenders.social_commerce_be.models.entities;

import com.thelastcodebenders.social_commerce_be.models.dto.OrderResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<Long> productIds;

    public OrderResponse toDto(List<ProductResponse> products) {
        double totalAmount = products.parallelStream().mapToDouble(ProductResponse::getAmount).sum();
        return OrderResponse.builder()
                .orderId(orderId)
                .userId(userId)
                .paid(paid)
                .totalAmount(totalAmount)
                .products(products)
                .build();
    }
}
