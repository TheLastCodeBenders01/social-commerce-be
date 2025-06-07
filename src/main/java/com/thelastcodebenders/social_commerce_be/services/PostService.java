package com.thelastcodebenders.social_commerce_be.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelastcodebenders.social_commerce_be.adapter.FileServiceAdapter;
import com.thelastcodebenders.social_commerce_be.exceptions.PostNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.CommentRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.PostRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.PostResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.Post;
import com.thelastcodebenders.social_commerce_be.models.entities.Comment;
import com.thelastcodebenders.social_commerce_be.models.entities.User;
import com.thelastcodebenders.social_commerce_be.repositories.PostRepository;
import com.thelastcodebenders.social_commerce_be.repositories.specifications.PostSpecification;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final FileServiceAdapter fileServiceAdapter;
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final CommentService commentService;

    @Transactional
    public PostResponse createPost(PostRequest request) throws JsonProcessingException {

        List<Long> productIds = new ArrayList<>();
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            productIds = objectMapper.readValue(request.getProductIds(), new TypeReference<List<Long>>() {});
        }

        // upload file
        String fileUrl = fileServiceAdapter.buildPinataFIleUri(fileServiceAdapter.uploadFileToPinata(request.getContent()));

        // save post
        Post post = Post.builder()
                .caption(request.getCaption())
                .contentUrl(fileUrl)
                .productIds(productIds)
                .userId(UserUtil.getLoggedInUser().getUserId())
                .build();

        postRepository.save(post);

        // build response
        List<ProductResponse> productResponses = buildProductResponsesFromProductIds(post.getProductIds());
        return post.toDto(UserUtil.getLoggedInUser(), productResponses, userService.findByUserId(post.getUserId()));
    }

    private Post getPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
    }

    public List<ProductResponse> buildProductResponsesFromProductIds(List<Long> productIds) {
        return productService.findAllById(productIds);
    }

//    public IdResponse addPostProducts(List<ProductRequest> productRequests) {
//        Post post = Post.builder()
//                .userId(UserUtil.getLoggedInUser().getUserId())
//                .build();
//        post.setProductIds(productService.saveProducts(productRequests));
//        post = savePost(post);
//
//        return IdResponse.builder()
//                .message("Products successfully added")
//                .id(post.getPostId())
//                .build();
//    }

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
        User user = UserUtil.getLoggedInUser();
        return posts.parallelStream().map(
                post -> post.toDto(user, productService.findAllById(post.getProductIds()), userService.findByUserId(post.getUserId()))
        ).toList();
    }

    public AppResponse likePost(Long postId) {
        Post post = getPostById(postId);
        UUID userId = UserUtil.getLoggedInUser().getUserId();
        String message;

        if (post.getLikeIds().contains(userId)) {
            post.getLikeIds().remove(userId);
            message = "Unliked";
        }
        else {
            post.getLikeIds().add(UserUtil.getLoggedInUser().getUserId());
            message = "Liked";
        }

        savePost(post);
        return AppResponse.builder()
                .message(message + " Post")
                .status(HttpStatus.OK)
                .build();
    }

    public Comment addCommentToPost(Long postId, CommentRequest request) {
        User user = UserUtil.getLoggedInUser();
        Comment comment = Comment.builder()
                .comment(request.getComment())
                .userId(user.getUserId())
                .profileImageUrl(user.getProfileImageUrl())
                .username(String.format("%s %s", user.getFirstName(), user.getLastName()))
                .build();

        Post post = getPostById(postId);
        post.getComments().add(comment);

        commentService.saveComment(comment);
        savePost(post);

        return comment;
    }
}

