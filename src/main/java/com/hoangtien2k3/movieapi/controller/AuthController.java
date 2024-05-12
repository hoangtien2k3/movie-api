package com.hoangtien2k3.movieapi.controller;

import com.hoangtien2k3.movieapi.auth.entities.RefreshToken;
import com.hoangtien2k3.movieapi.auth.service.AuthService;
import com.hoangtien2k3.movieapi.auth.service.JwtService;
import com.hoangtien2k3.movieapi.auth.service.RefreshTokenService;
import com.hoangtien2k3.movieapi.auth.utils.request.LoginRequest;
import com.hoangtien2k3.movieapi.auth.utils.request.RefreshTokenRequest;
import com.hoangtien2k3.movieapi.auth.utils.request.RegisterRequest;
import com.hoangtien2k3.movieapi.auth.utils.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> register(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        String accessToken = jwtService.generateToken(refreshToken.getUser());

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}
