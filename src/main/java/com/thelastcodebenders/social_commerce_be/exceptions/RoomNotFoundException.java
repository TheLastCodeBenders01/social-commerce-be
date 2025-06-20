package com.thelastcodebenders.social_commerce_be.exceptions;

public class RoomNotFoundException extends EntityNotFoundException {
    public RoomNotFoundException() {
        super ("Room");
    }
}
