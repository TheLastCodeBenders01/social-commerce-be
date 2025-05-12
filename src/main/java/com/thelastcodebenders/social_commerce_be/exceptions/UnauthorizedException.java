package com.thelastcodebenders.social_commerce_be.exceptions;

public class UnauthorizedException extends SocialCommerceException {
    public UnauthorizedException(String message) {
        super("Unable to access this resource due to " + message);
    }
}
