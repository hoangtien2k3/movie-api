package com.hoangtien2k3.movieapi.exceptions.payload;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {}

    public UserNotFoundException(String message) {
        super(message);
    }
}
