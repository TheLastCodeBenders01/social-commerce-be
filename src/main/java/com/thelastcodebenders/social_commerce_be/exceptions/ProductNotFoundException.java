package com.thelastcodebenders.social_commerce_be.exceptions;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException() {
        super("PRODUCT");
    }
}
