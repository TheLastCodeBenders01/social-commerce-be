package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID userId;

    private String firstName;
    private String lastName;
    private String email;
}
