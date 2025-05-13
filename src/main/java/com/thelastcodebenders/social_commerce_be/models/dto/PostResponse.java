package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PostResponse {
    private long postId;
    private String contentUrl;
    private String caption;
    private UUID userId;
    private List<ProductResponse> products;
}
