package com.thelastcodebenders.social_commerce_be.exceptions;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException() {
        super("Post");
    }
}
