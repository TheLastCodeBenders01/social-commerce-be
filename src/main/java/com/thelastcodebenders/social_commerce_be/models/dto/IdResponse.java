package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdResponse {
    private String id;
    private String message;
}
