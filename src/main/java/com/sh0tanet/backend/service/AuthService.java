package com.sh0tanet.backend.service;

import com.sh0tanet.backend.dto.*;

public interface AuthService {
    AuthResponse register(AuthRegisterRequest request);
    AuthResponse login(AuthLoginRequest request);
}