package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.adapter.FileServiceAdapter;
import com.thelastcodebenders.social_commerce_be.exceptions.UserNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.UserProfileRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.UserResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.User;
import com.thelastcodebenders.social_commerce_be.repositories.UserRepository;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileServiceAdapter fileServiceAdapter;

    public User findByUserId(UUID userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public UserResponse getLoggedInUser() {
        User user = UserUtil.getLoggedInUser();
        return getUserById(user.getUserId());
    }

    public UserResponse getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new).toDto();
    }

    @Transactional
    public UserResponse updateUserProfile(UserProfileRequest request) {
        User user = UserUtil.getLoggedInUser();

        user.setActivated(true);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setStreetAddress(request.getStreetAddress());
        user.setState(request.getState());
        user.setCountry(request.getCountry());

        if (request.getProfileImage() != null) {
            user.setProfileImageUrl(fileServiceAdapter.buildPinataFIleUri(fileServiceAdapter.uploadFileToPinata(request.getProfileImage())));
        }

        saveUser(user);
        return user.toDto();
    }

    @Transactional
    public AppResponse followUser(UUID userId) {
        User loggedInUser = UserUtil.getLoggedInUser();
        User toBeFollowed = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String message;

        if (loggedInUser.getFollowingIds().contains(toBeFollowed.getUserId())) {
            loggedInUser.getFollowingIds().remove(toBeFollowed.getUserId());
            toBeFollowed.getFollowerIds().remove(loggedInUser.getUserId());
            message = "Unfollow";
        }
        else {
            loggedInUser.getFollowingIds().add(toBeFollowed.getUserId());
            toBeFollowed.getFollowerIds().add(loggedInUser.getUserId());
            message = "Follow";
        }

        saveUser(loggedInUser);
        saveUser(toBeFollowed);

        return AppResponse.builder()
                .message(message + " success")
                .status(HttpStatus.OK)
                .build();
    }
}
