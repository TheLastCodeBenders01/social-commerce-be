package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.UserProfileRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.UserResponse;
import com.thelastcodebenders.social_commerce_be.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @PutMapping("profile")
    public UserResponse updateUserProfile(@RequestBody UserProfileRequest request) {
        return userService.updateUserProfile(request);
    }
}
