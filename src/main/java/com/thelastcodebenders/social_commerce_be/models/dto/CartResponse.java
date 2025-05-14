package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponse {
    private long cartId;
    private double totalAmount;
    private List<ProductResponse> products;
}
