package com.thelastcodebenders.social_commerce_be.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thelastcodebenders.social_commerce_be.models.dto.UserResponse;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Builder.Default private UUID userId = UUID.randomUUID();

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Builder.Default private boolean activated = false;

    private String phoneNumber;
    private String streetAddress;
    private String state;
    private String country;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_followers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_id")
    @Builder.Default private List<UUID> followerIds = new ArrayList<>();

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_followings", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_id")
    private List<UUID> followingIds = new ArrayList<>();

    @Builder.Default private boolean isPrinter = false;
    @Builder.Default private boolean accountNonExpired = true;
    @Builder.Default private boolean accountNonLocked = true;
    @Builder.Default private boolean enabled = true;
    @Builder.Default private boolean credentialsNonExpired = true;
    @Builder.Default private boolean emailVerified = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    public UserResponse toDto() {
        return UserResponse.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .activated(activated)
                .phoneNumber(phoneNumber)
                .followerIds(followerIds)
                .followingIds(followingIds)
                .address(UserResponse.Address.builder()
                        .streetAddress(streetAddress)
                        .state(state)
                        .country(country)
                        .build())
                .build();
    }
}
