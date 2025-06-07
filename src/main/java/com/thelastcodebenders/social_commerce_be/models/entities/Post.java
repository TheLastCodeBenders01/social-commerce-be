package com.thelastcodebenders.social_commerce_be.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thelastcodebenders.social_commerce_be.models.dto.PostResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postId;

    private String contentUrl;
    private String caption;

    private UUID userId;

    @Builder.Default private Instant createdAt = Instant.now();
    @Builder.Default private Instant updatedAt = Instant.now();

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_products", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "product_id")
    @Builder.Default private List<Long> productIds = new ArrayList<>();

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "liker_id")
    @Builder.Default private List<UUID> likeIds = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id") // This creates a post_id foreign key in the comment table
    private List<Comment> comments = new ArrayList<>();

    public PostResponse toDto(User user, List<ProductResponse> products, User postUser) {
        return PostResponse.builder()
                .postId(postId)
                .contentUrl(contentUrl)
                .caption(caption)
                .products(products)
                .userId(userId)
                .likes(likeIds.parallelStream().count())
                .fullName(String.format("%s %s", postUser.getFirstName(), postUser.getLastName()))
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .liked(
                        likeIds.contains(user.getUserId())
                )
                .profileImageUrl(user.getProfileImageUrl())
                .comments(comments)
                .build();
    }
}
