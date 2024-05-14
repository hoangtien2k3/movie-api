package com.hoangtien2k3.movieapi.controller;

import com.hoangtien2k3.movieapi.dto.response.UserResponse;
import com.hoangtien2k3.movieapi.service.auth.AuthService;
import com.hoangtien2k3.movieapi.service.auth.JwtService;
import com.hoangtien2k3.movieapi.service.auth.RefreshTokenService;
import com.hoangtien2k3.movieapi.dto.request.LoginRequest;
import com.hoangtien2k3.movieapi.dto.request.RefreshTokenRequest;
import com.hoangtien2k3.movieapi.dto.request.RegisterRequest;
import com.hoangtien2k3.movieapi.dto.response.AuthResponse;
import com.hoangtien2k3.movieapi.dto.ApiResponse;
import com.hoangtien2k3.movieapi.utils.AppMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping({"/register", "/signup"})
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        int statusCode = HttpStatus.CREATED.value();
        String message = AppMessage.ACCOUNT_CREATED_SUCCESS.getMessage();
        return ResponseEntity.status(statusCode)
                .body(buildApiResponse(authService.register(registerRequest), statusCode, message));
    }

    @PostMapping({"/login", "/signin"})
    public ApiResponse<AuthResponse> register(@RequestBody LoginRequest loginRequest) {
        int statusCode = HttpStatus.CREATED.value();
        String message = AppMessage.ACCOUNT_LOGIN_SUCCESS.getMessage();
        return buildApiResponse(authService.login(loginRequest), statusCode, message);
    }

    @PostMapping({"/refresh", "refresh-token"})
    public ApiResponse<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        int statusCode = HttpStatus.OK.value();
        String message = AppMessage.REFRESH_TOKEN_SUCCESS.getMessage();
        return buildApiResponse(authService.refreshToken(refreshTokenRequest), statusCode, message);
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(authService.getMyInfo())
                .build();
    }

    public <T> ApiResponse<T> buildApiResponse(T response, int status, String message) {
        return ApiResponse.<T>builder()
                .code(status)
                .message(message)
                .result(response)
                .build();
    }

}
