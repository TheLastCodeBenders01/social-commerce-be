package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostRequest {
    private MultipartFile content;
    private String caption;
}
