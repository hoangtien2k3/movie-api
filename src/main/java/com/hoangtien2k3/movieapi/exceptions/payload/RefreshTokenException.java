package com.hoangtien2k3.movieapi.exceptions.payload;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException() {}

    public RefreshTokenException(String message) {
        super(message);
    }
}
