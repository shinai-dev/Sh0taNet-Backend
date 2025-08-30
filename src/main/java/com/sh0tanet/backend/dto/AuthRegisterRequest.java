package com.sh0tanet.backend.dto;

import com.sh0tanet.backend.validation.PasswordMatches;
import jakarta.validation.constraints.*;

@PasswordMatches
public record AuthRegisterRequest(
        @NotBlank @Size(max = 80) String firstName,
        @NotBlank @Size(max = 80) String lastName,
        @Email @NotBlank @Size(max = 160) String email,
        @NotBlank @Size(min = 8, max = 72) String password,
        @NotBlank String confirmPassword,
        @AssertTrue(message = "Debe aceptar los términos y condiciones") boolean agreeToTerms
) {}