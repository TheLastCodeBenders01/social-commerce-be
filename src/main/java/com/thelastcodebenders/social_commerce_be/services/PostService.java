package com.thelastcodebenders.social_commerce_be.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelastcodebenders.social_commerce_be.adapter.FileServiceAdapter;
import com.thelastcodebenders.social_commerce_be.exceptions.PostNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.IdResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.PostRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.PostResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.Post;
import com.thelastcodebenders.social_commerce_be.repositories.PostRepository;
import com.thelastcodebenders.social_commerce_be.repositories.specifications.PostSpecification;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final FileServiceAdapter fileServiceAdapter;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Transactional
    public PostResponse createPost(PostRequest request) throws JsonProcessingException {

        List<Long> productIds = new ArrayList<>();
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            productIds = objectMapper.readValue(request.getProductIds(), new TypeReference<List<Long>>() {});
        }

        // upload file
        String fileUrl = fileServiceAdapter.buildFileUri(fileServiceAdapter.uploadFile(request.getContent()));

        // save post
        Post post = Post.builder()
                .caption(request.getCaption())
                .contentUrl(fileUrl)
                .productIds(productIds)
                .build();

        postRepository.save(post);

        // build response
        List<ProductResponse> productResponses = buildProductResponsesFromProductIds(post.getProductIds());
        return post.toDto(productResponses);
    }

    private Post getPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
    }

    private List<ProductResponse> buildProductResponsesFromProductIds(List<Long> productIds) {
        return productService.findAllById(productIds);
    }

    public IdResponse addPostProducts(List<ProductRequest> productRequests) {
        Post post = Post.builder()
                .userId(UserUtil.getLoggedInUser().getUserId())
                .build();
        post.setProductIds(productService.saveProducts(productRequests));
        post = savePost(post);

        return IdResponse.builder()
                .message("Products successfully added")
                .id(post.getPostId())
                .build();
    }

    private Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<PostResponse> getAllPosts(int pageSize, int pageNumber) {
        return convertPostListToPostListResponse(postRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent());
    }

    public List<PostResponse> searchPosts(int pageSize, int pageNumber, String tag) {
        Specification<Post> specification = PostSpecification.search(tag);
        return convertPostListToPostListResponse(
                postRepository.findAll(specification, PageRequest.of(pageNumber, pageSize)).getContent()
        );
    }

    public List<PostResponse> convertPostListToPostListResponse(List<Post> posts) {
        return posts.parallelStream().map(
                post -> post.toDto(productService.findAllById(post.getProductIds()))
        ).toList();
    }
}
