package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.IdResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.PostRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.PostResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductRequest;
import com.thelastcodebenders.social_commerce_be.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("posts")
@RestController
public class PostController {
    private final PostService postService;

    @Operation(summary = "add product to post, this endpoint returns the post id that will be used for the other endpoint to add content to post")
    @PostMapping("products")
    public IdResponse addPostProducts(@RequestBody List<ProductRequest> request) {
        return postService.addPostProducts(request);
    }

    @Operation(summary = "add post using the id gotten from the product endpoint")
    @PutMapping("{postId}")
    public PostResponse addPost(@ModelAttribute PostRequest request, @PathVariable long postId) {
        return postService.createPost(request, postId);
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
