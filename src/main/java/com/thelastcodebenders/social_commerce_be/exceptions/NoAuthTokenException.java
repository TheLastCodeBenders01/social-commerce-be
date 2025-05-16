package com.thelastcodebenders.social_commerce_be.exceptions;

public class NoAuthTokenException extends UnauthorizedException {
    public NoAuthTokenException() {
        super("No Auth Token Found or Invalid Auth Token");
    }
}
