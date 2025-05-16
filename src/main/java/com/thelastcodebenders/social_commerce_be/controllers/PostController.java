package com.thelastcodebenders.social_commerce_be.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thelastcodebenders.social_commerce_be.models.dto.PostRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.PostResponse;
import com.thelastcodebenders.social_commerce_be.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("posts")
@RestController
public class PostController {
    private final PostService postService;

    @Operation(summary = "add post using the id gotten from the product endpoint")
    @PostMapping
    public PostResponse addPost(@ModelAttribute PostRequest request) throws JsonProcessingException {
        return postService.createPost(request);
    }

    @Operation(summary = "get all posts")
    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        return postService.getAllPosts(pageSize, pageNumber);
    }

    @Operation(summary = "search posts")
    @GetMapping("search")
    public List<PostResponse> searchPosts(
            @RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber, @RequestParam("tag") String tag
    ) {
        return postService.searchPosts(pageSize, pageNumber, tag);
    }
}
