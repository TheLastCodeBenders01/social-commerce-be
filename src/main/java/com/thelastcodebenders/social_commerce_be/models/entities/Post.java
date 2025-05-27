package com.thelastcodebenders.social_commerce_be.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thelastcodebenders.social_commerce_be.models.dto.PostResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public PostResponse toDto(List<ProductResponse> products) {
        return PostResponse.builder()
                .postId(postId)
                .contentUrl(contentUrl)
                .caption(caption)
                .products(products)
                .userId(userId)
                .likes(likeIds.parallelStream().count())
                .build();
    }
}
