package com.thelastcodebenders.social_commerce_be.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thelastcodebenders.social_commerce_be.models.dto.types.Currency;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
@Data
@Builder
public class InitiateCheckoutRequest {
    private CustomerDetails customer;
    private double amount;
    private Currency currency;
    private UUID reference;
    private String processor;
    @JsonProperty("redirect_url")
    private String redirectUrl;
    @JsonProperty("notification_url")
    private String notificationUrl;
    private String narration;
    private String mode;
}
