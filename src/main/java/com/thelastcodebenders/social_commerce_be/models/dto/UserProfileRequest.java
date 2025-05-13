package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Data;

@Data
public class UserProfileRequest {
    private String phoneNumber;
    private String streetAddress;
    private String state;
    private String country;
}
