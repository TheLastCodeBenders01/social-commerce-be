package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.exceptions.CartNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.CartResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.Cart;
import com.thelastcodebenders.social_commerce_be.repositories.CartRepository;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartResponse addItemToCart(Long productId) {
        Cart cart = cartRepository.findByUserId(UserUtil.getLoggedInUser().getUserId()).orElseGet(
                this::createUserCart
        );
        cart.getProductIds().add(productId);
        cart = saveCart(cart);

        return buildCartResponseFromCart(cart);
    }

    public Cart createUserCart() {
        return saveCart(
                Cart.builder()
                        .userId(UserUtil.getLoggedInUser().getUserId())
                        .productIds(new ArrayList<>())
                        .build()
        );
    }

    public void addCartToUserId(UUID userId) {
        saveCart(
                Cart.builder()
                        .userId(userId)
                        .productIds(new ArrayList<>())
                        .build()
        );
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public CartResponse buildCartResponseFromCart(Cart cart) {
        return CartResponse.builder()
                .cartId(cart.getCartId())
                .totalAmount(productService.sumAmountByProductIds(cart.getProductIds()))
                .products(
                        cart.getProductIds().parallelStream().map(
                                productId -> productService.getProductById(productId).toDto()
                        ).toList()
                )
                .build();
    }

    public CartResponse getCartByUser() {
        return buildCartResponseFromCart(cartRepository.findByUserId(UserUtil.getLoggedInUser().getUserId()).orElseGet(
                this::createUserCart
        ));
    }

    public Cart getLoggedInUserCart() {
        return cartRepository.findByUserId(UserUtil.getLoggedInUser().getUserId()).get();
    }

    @Transactional
    public void removeItemsInUserCart(UUID userid) {
        Cart cart = cartRepository.findByUserId(userid).get();
        cart.setProductIds(new ArrayList<>());

        saveCart(cart);
    }

    public CartResponse removeItemFromCart(Long productId) {
        Cart cart = cartRepository.findByUserId(UserUtil.getLoggedInUser().getUserId()).orElseThrow(CartNotFoundException::new);

        cart.getProductIds().remove(productId);
        cart = saveCart(cart);

        return buildCartResponseFromCart(cart);
    }

    public AppResponse clearLoggedInUserCart() {
        Cart cart = cartRepository.findByUserId(UserUtil.getLoggedInUser().getUserId()).get();
        cart.setProductIds(new ArrayList<>());
        saveCart(cart);

        return AppResponse.builder()
                .message("cart successfully cleared")
                .status(HttpStatus.OK)
                .build();
    }
}
