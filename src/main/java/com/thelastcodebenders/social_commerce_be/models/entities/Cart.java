package com.thelastcodebenders.social_commerce_be.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartId;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"))
    @Column(name = "product_id")
    @Builder.Default private List<Long> productIds = new ArrayList<>();

    @Column(unique = true)
    private UUID userId;
}
