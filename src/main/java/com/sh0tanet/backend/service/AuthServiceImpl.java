package com.sh0tanet.backend.service;

import com.sh0tanet.backend.dto.*;
import com.sh0tanet.backend.exception.EmailAlreadyInUseException;
import com.sh0tanet.backend.exception.InvalidCredentialsException;
import com.sh0tanet.backend.mapper.UserMapper;
import com.sh0tanet.backend.model.Role;
import com.sh0tanet.backend.model.User;
import com.sh0tanet.backend.repository.UserRepository;
import com.sh0tanet.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthResponse register(AuthRegisterRequest request) {
        var email = request.email().toLowerCase().trim();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException(
                    "The provided email address is already registered. Please use a different one.");
        }

        var user = User.create(
                request.firstName().trim(),
                request.lastName().trim(),
                email,
                encoder.encode(request.password()),
                Set.of(Role.USER));

        userRepository.save(user);

        var token = jwtService.generateToken(
                user.getEmail(),
                Map.of("roles", user.getRoles(), "uid", user.getId()));

        return new AuthResponse(token, UserMapper.toResponse(user));
    }

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email().toLowerCase().trim(),
                            request.password()));
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException(
                    " Please check your email and password and try again.");
        }

        var user = userRepository.findByEmail(request.email().toLowerCase().trim())
                .orElseThrow(
                        () -> new InvalidCredentialsException(" The user account was not found."));

        var token = jwtService.generateToken(
                user.getEmail(),
                Map.of("roles", user.getRoles(), "uid", user.getId()));

        return new AuthResponse(token, UserMapper.toResponse(user));
    }

}