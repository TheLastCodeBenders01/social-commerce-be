package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.exceptions.UserNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.LoginResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.UserRequest;
import com.thelastcodebenders.social_commerce_be.models.entities.User;
import com.thelastcodebenders.social_commerce_be.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CartService cartService;

    @Transactional
    public void signUp(UserRequest userRequest) {
        log.info("Got into the authentication service");
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .profileImageUrl(userRequest.getProfileImageUrl())
                .build();

        userRepository.save(user);

        cartService.addCartToUserId(user.getUserId());
        log.info("Created User: {}", user);
    }

    public LoginResponse oauthLogin(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .build();
    }
}
