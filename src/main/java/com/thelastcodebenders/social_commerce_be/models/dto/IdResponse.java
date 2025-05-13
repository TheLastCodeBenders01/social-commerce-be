package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdResponse {
    private long id;
    private String message;
}
