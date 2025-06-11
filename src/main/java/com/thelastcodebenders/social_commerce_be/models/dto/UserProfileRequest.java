package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserProfileRequest {
    private String phoneNumber;
    private String streetAddress;
    private String state;
    private String country;
    private MultipartFile profileImage;
}
