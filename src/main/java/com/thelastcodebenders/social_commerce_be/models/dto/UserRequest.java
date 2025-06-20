package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profileImageUrl;
}
