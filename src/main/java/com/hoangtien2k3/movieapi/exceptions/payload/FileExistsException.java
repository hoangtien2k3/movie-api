package com.hoangtien2k3.movieapi.exceptions.payload;

public class FileExistsException extends RuntimeException {
    public FileExistsException(String message) {
        super(message);
    }
}
