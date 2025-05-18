package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class KorapayWebhookData {

    private String reference;
    private int amount;
    private PaymentStatus status;
    private PaymentMethod payment_method;
}
