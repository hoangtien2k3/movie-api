package com.hoangtien2k3.movieapi.exceptions.payload;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
