package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class KorapayWebHookData {
    private CustomerDetails customer;
    private Double amount;
    private String reference;
    private String currency;
    private Object metadata;
}
