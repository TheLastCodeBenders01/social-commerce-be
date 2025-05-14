package com.thelastcodebenders.social_commerce_be.repositories;

import com.thelastcodebenders.social_commerce_be.models.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(UUID userId);
}
