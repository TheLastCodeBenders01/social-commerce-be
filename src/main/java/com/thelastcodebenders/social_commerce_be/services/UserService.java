package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.exceptions.UserNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.UserResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.User;
import com.thelastcodebenders.social_commerce_be.repositories.UserRepository;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

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

        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public UserResponse getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new).toDto();
    }
}
