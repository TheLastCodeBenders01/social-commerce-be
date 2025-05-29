package com.thelastcodebenders.social_commerce_be.exceptions;

public class CartNotFoundException extends EntityNotFoundException {
    public CartNotFoundException() {
        super("CART");
    }
}
