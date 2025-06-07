package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID userId;

    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;

    private String phoneNumber;
    private Address address;
    private List<UUID> followerIds;
    private List<UUID> followingIds;
    private String profileImageUrl;

    @Data
    @Builder
    public static class Address {
        private String streetAddress;
        private String state;
        private String country;
    }
}
