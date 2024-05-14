package com.hoangtien2k3.movieapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppMessage {
    SUCCESS("successful"),
    FAILURE("failed"),
    ACCOUNT_LOGIN_SUCCESS("Account login successful"),
    ACCOUNT_CREATED_SUCCESS("Account created successfully"),
    ACCOUNT_CREATION_FAILED("Failed to create account"),
    ACCOUNT_UPDATED_SUCCESS("Account updated successfully"),
    ACCOUNT_UPDATED_FAILED("Failed to update account"),
    ACCOUNT_DELETED_SUCCESS("Account deleted successfully"),
    ACCOUNT_DELETED_FAILED("Failed to delete account"),
    REFRESH_TOKEN_SUCCESS("Refresh token successful"),
    REFRESH_TOKEN_FAILED("Refresh token has expired. Please log in again"),
    MOVIE_CREATION_SUCCESS("Movie creation successful");

    private final String message;
}
