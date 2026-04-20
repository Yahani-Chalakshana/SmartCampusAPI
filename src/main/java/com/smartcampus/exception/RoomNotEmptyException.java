package com.smartcampus.exception;

// This custom exception handles the business logic constraint 
// where a room cannot be deleted if it still has active sensors.
public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String message) {
        super(message);
    }
}