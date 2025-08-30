package com.sh0tanet.backend.dto;

public record AuthResponse(
        String tokenType,
        String accessToken,
        UserResponse user
) {
    public AuthResponse(String accessToken, UserResponse user) {
        this("Bearer", accessToken, user);
    }
}