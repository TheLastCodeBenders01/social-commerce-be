package com.thelastcodebenders.social_commerce_be.models.dto;

import com.thelastcodebenders.social_commerce_be.models.entities.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
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
    private long likes;
    private String fullName;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean liked;
    private List<Comment> comments;
    private String profileImageUrl;
}
