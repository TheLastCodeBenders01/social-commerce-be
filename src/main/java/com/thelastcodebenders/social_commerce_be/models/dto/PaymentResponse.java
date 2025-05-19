package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private boolean status;
    private String message;
    private String reference;
    private String checkout_url;
    private String processor;
}

