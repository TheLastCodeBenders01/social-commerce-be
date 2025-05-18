package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Data;

@Data
public class KorapayWebhookRequest {

    private String event;
    private KorapayWebhookData data;
}
