package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoginResponse {

    private String token;
    private UUID userId;
}
