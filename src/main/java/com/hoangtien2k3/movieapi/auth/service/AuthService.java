package com.hoangtien2k3.movieapi.auth.service;

import com.hoangtien2k3.movieapi.auth.dto.request.RefreshTokenRequest;
import com.hoangtien2k3.movieapi.auth.dto.response.UserResponse;
import com.hoangtien2k3.movieapi.auth.entities.RefreshToken;
import com.hoangtien2k3.movieapi.auth.entities.User;
import com.hoangtien2k3.movieapi.auth.mapper.UserMapper;
import com.hoangtien2k3.movieapi.auth.repositories.UserRepository;
import com.hoangtien2k3.movieapi.auth.dto.request.LoginRequest;
import com.hoangtien2k3.movieapi.auth.dto.request.RegisterRequest;
import com.hoangtien2k3.movieapi.auth.dto.response.AuthResponse;
import com.hoangtien2k3.movieapi.exceptions.AppException;
import com.hoangtien2k3.movieapi.exceptions.ErrorCode;
import com.hoangtien2k3.movieapi.exceptions.payload.RefreshTokenException;
import com.hoangtien2k3.movieapi.exceptions.payload.UserNotFoundException;
import com.hoangtien2k3.movieapi.utils.AppMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRoles());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequest.getEmail()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Set<GrantedAuthority> authorities = new HashSet<>(userDetails.getAuthorities());
        // Add roles from database to authorities
        // Assuming your user entity has a method to get roles
        authorities.addAll(userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequest.getEmail())).getRole());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());

        //check refreshToken isExpired
        if (refreshToken.getExpirationTime().isBefore(Instant.now())) {
            throw new RefreshTokenException(AppMessage.REFRESH_TOKEN_FAILED.getMessage());
        }
        // generate new token by refreshToken
        String accessToken = jwtService.generateToken(refreshToken.getUser());

        return new AuthResponse(accessToken, refreshToken.getRefreshToken());
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

}
