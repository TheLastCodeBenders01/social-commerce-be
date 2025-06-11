package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.UserProfileRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.UserResponse;
import com.thelastcodebenders.social_commerce_be.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "update user profile")
    @PutMapping("profile")
    public UserResponse updateUserProfile(@ModelAttribute UserProfileRequest request) {
        return userService.updateUserProfile(request);
    }

    @Operation(summary = "update user profile")
    @GetMapping("get-logged-in-user")
    public UserResponse getLoggedInUser() {
        return userService.getLoggedInUser();
    }

    @Operation(summary = "follow user")
    @PutMapping("follow/{userId}")
    public AppResponse followUser(@PathVariable UUID userId) {
        return userService.followUser(userId);
    }

    @Operation(summary = "get user by id")
    @GetMapping("{userId}")
    public UserResponse getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }
}
