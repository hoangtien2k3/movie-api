package com.hoangtien2k3.movieapi.controller;

import com.hoangtien2k3.movieapi.dto.request.UserUpdateRequest;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping({"/register", "/signup"})
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        int statusCode = HttpStatus.CREATED.value();
        String message = AppMessage.ACCOUNT_CREATED_SUCCESS.getMessage();
        return ResponseEntity.status(statusCode)
                .body(buildApiResponse(authService.register(registerRequest), statusCode, message));
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request")
    })
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
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(authService.getMyInfo())
                .build();
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "No content"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable Integer userId) {
        authService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "No content"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Integer userId,
                                                @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(authService.updateUser(userId, request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {

        return ApiResponse.<List<UserResponse>>builder()
                .result(authService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Integer userId) {
        return ApiResponse.<UserResponse>builder()
                .result(authService.getUser(userId))
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
