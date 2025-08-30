package com.sh0tanet.backend.dto;

import java.time.Instant;
import java.util.Set;

public record UserProfileResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String username,
        Set<String> roles,
        boolean enabled,
        Instant createdAt,
        String avatarUrl
) {}