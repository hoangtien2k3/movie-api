package com.hoangtien2k3.movieapi.exceptions.payload;

public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException() {}

    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
