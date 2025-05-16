package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostRequest {
    private MultipartFile content;
    private String caption;
    private String productIds;
}
