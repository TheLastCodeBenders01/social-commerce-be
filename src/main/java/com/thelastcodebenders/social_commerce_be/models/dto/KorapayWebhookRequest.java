package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class KorapayWebhookRequest {

    private String event;
    private String reference;
    private KorapayWebHookData data;
}
