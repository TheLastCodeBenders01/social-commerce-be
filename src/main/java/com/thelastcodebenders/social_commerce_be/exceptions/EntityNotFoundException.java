package com.thelastcodebenders.social_commerce_be.exceptions;

public class EntityNotFoundException extends SocialCommerceException {

    EntityNotFoundException(String ENTITY) {
        super(ENTITY + " not found");
    }
}
