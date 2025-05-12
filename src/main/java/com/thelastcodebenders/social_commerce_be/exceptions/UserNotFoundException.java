package com.thelastcodebenders.social_commerce_be.exceptions;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super("USER");
    }
}
