package com.thelastcodebenders.social_commerce_be.exceptions;

public class OrderNotFoundException extends EntityNotFoundException {
   public OrderNotFoundException() {
        super("Order");
    }
}
